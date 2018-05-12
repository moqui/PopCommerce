/*
 * This software is in the public domain under CC0 1.0 Universal plus a
 * Grant of Patent License.
 *
 * To the extent possible under law, the author(s) have dedicated all
 * copyright and related and neighboring rights to this software to the
 * public domain worldwide. This software is distributed without any
 * warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication
 * along with this software (see the LICENSE.md file). If not, see
 * <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

import org.moqui.Moqui
import org.moqui.context.ExecutionContext
import org.moqui.screen.ScreenTest
import org.moqui.screen.ScreenTest.ScreenTestRender
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class PopcAdminScreenTests extends Specification {
    protected final static Logger logger = LoggerFactory.getLogger(PopcAdminScreenTests.class)

    @Shared
    ExecutionContext ec
    @Shared
    ScreenTest screenTest
    @Shared
    long effectiveTime = System.currentTimeMillis()

    def setupSpec() {
        ec = Moqui.getExecutionContext()
        ec.user.loginUser("john.doe", "moqui")
        screenTest = ec.screen.makeTest().baseScreenPath("apps/PopcAdmin")

        ec.entity.tempSetSequencedIdPrimary("mantle.order.OrderHeader", 63100, 10)
        ec.entity.tempSetSequencedIdPrimary("mantle.work.effort.WorkEffort", 63100, 10)
    }

    def cleanupSpec() {
        long totalTime = System.currentTimeMillis() - screenTest.startTime
        logger.info("Rendered ${screenTest.renderCount} screens (${screenTest.errorCount} errors) in ${ec.l10n.format(totalTime/1000, "0.000")}s, output ${ec.l10n.format(screenTest.renderTotalChars/1000, "#,##0")}k chars")

        ec.entity.tempResetSequencedIdPrimary("mantle.order.OrderHeader")
        ec.entity.tempResetSequencedIdPrimary("mantle.work.effort.WorkEffort")
        ec.destroy()
    }

    def setup() {
        ec.artifactExecution.disableAuthz()
    }

    def cleanup() {
        ec.artifactExecution.enableAuthz()
    }

    def "render POP Commerce Admin screens with no required parameters"() {
        when:
        Set<String> screensToSkip = new HashSet(['wiki', 'EditWikiPage', 'WikiCommentNested', 'WikiCommentReply', 'FindCustomer', 'FindSupplier'])
        List<String> screenPaths = screenTest.getNoRequiredParameterPaths(screensToSkip)
        for (String screenPath in screenPaths) {
            ScreenTestRender str = screenTest.render(screenPath, [lastStandalone:"-2"], null)
            logger.info("Rendered ${screenPath} in ${str.getRenderTime()}ms, ${str.output?.length()} characters")
        }

        then:
        screenTest.errorCount == 0
    }

    @Unroll
    def "render POP Commerce Admin screen (#screenPath, #containsTextList)"() {
        setup:
        ScreenTestRender str = screenTest.render(screenPath, [lastStandalone:"-2"], null)
        // logger.info("Rendered ${screenPath} in ${str.getRenderTime()}ms, output:\n${str.output}")
        boolean containsAll = true
        for (String containsText in containsTextList) {
            boolean contains = containsText ? str.assertContains(containsText) : true
            if (!contains) {
                logger.info("In ${screenPath} text [${containsText}] not found:\n${str.output}")
                containsAll = false
            }

        }

        expect:
        !str.errorMessages
        containsAll

        where:
        screenPath | containsTextList

        // Supplier
        "Supplier/EditSupplier?partyId=ZiddlemanInc" |
                ['Ziddleman &amp; Sons Suppliers', 'Supplier', '1-702-987-6543', '1350 E. Flamingo Rd. #9876']

        // Customer
        "Customer/EditCustomer?partyId=CustJqp" |
                ['Joe', 'joe@public.com', 'Visa ************1111', '1-702-234-5678', '1350 E. Flamingo Rd. #2345']

        // Party
        "Party/EditParty?partyId=EX_JOHN_DOE" | ['john.doe@moqui.org', 'John']

        // Catalog
        "Catalog/Category/EditCategory?productCategoryId=DEMO_ONE" | ['Demo Category One']
        "Catalog/Category/EditProducts?productCategoryId=DEMO_ONE" | ['Demo Product One-One', 'Asset (Good)']

        "Catalog/Product/EditProduct?productId=DEMO_1_1" | ['Demo Product One-One', 'Shipping Weight']
        "Catalog/Product/EditCategories?productId=DEMO_1_1" | ['Demo Category One', 'Catalog']
        "Catalog/Product/EditAssocs?productId=DEMO_1_1" | []
        "Catalog/Product/EditPrices?productId=DEMO_1_1" | ['ZiddlemanInc', '16.99']
        "Catalog/Search?queryString=demo one" | ['Demo Product One-One', 'Asset (Good)']

        // TODO: Feature and FeatureGroup, need to add demo data

        // Order
        "Order/OrderDetail?orderId=55400" | ['$23,795.00', 'Shipment 55400', 'Demo Product One-One'] // Purchase Order
        "Order/PrintOrder?orderId=55400&renderMode=xsl-fo" | ['Ziziwork Retail', 'Demo Product', '$23,795.00']
        "Order/OrderDetail?orderId=55401" | ['$9,000.00', 'Shipment 55401', 'Picker Bot 2000'] // Sales Order

        // TODO Picking, call transitions or add other tests to create picklists, etc

        // Shipment
        "Shipment/ShipmentDetail?shipmentId=55400" | ['Invoice 55400', 'Demo Product One-One'] // Incoming Shipment
        "Shipment/ShipmentDetail?shipmentId=55401" | ['Order 55401', 'Picker Bot 2000'] // Outgoing Shipment

        // Facility
        "Facility/EditFacility?facilityId=ZIRET_WH" | ['Ziziwork Retail Warehouse', '51 W. Center St.']
        "Facility/EditFacilityLocations?facilityId=ZIRET_WH" | []
        "Facility/FacilityCalendar?facilityId=ZIRET_WH" | []

        // Asset
        "Asset/Asset/AssetDetail?assetId=55400" | ['DEMO_1_1', 'ORG_ZIZI_RETAIL', 'Current: Available']
        "Asset/Asset/AssetCalendar?assetId=55400" | []

        // Accounting/Invoice
        "Accounting/Invoice/FindInvoice?statusId_op=in&statusId=InvoiceReceived,InvoiceApproved&toPartyId=ORG_ZIZI_RETAIL" |
                ['Ziddleman', 'Ziziwork Retail', 'Sales/Purchase']
        "Accounting/Invoice/EditInvoice?invoiceId=55100" | ['Current: Approved', 'Unpaid $1,824.25', 'ORG_ZIZI_RETAIL']
        "Accounting/Invoice/EditInvoice?invoiceId=55400" |
                ['ORG_ZIZI_RETAIL', 'Current: Payment Sent', 'Applied Payments $23,830.00']
        "Accounting/Invoice/EditInvoiceItems?invoiceId=55400" | ['Demo Product One-One', 'Shipping and Handling']
        "Accounting/Invoice/PrintInvoice?invoiceId=55400&renderMode=xsl-fo" |
                ['1350 E. Flamingo Rd. #9876, Las Vegas, NV 89119-5263', 'Asset - Inventory']

        // Accounting/Payment
        "Accounting/Payment/EditPayment?paymentId=55400" |
                ['ORG_ZIZI_RETAIL', 'Applied $24,000.00', 'Current: Delivered']
        "Accounting/Payment/PaymentCheck?paymentIds=55400&renderMode=xsl-fo" | ['Ziddleman',
                'Twenty four thousand and 00/100', 'Picker Bot 2000']
        "Accounting/Payment/PaymentDetail?paymentIds=55400&renderMode=xsl-fo" |
                ['Ziddleman', '$23,830.00', 'Picker Bot 2000']

        // Accounting Other
        "Accounting/FinancialAccount/EditFinancialAccount?finAccountId=55700" |
                ['Ziziwork Retail', 'Joe Public', 'Current: Active']
        "Accounting/FinancialAccount/FinancialAccountTrans?finAccountId=55700" |
                ['Customer Service Credit', 'Ziziwork Retail ']
        "Accounting/Transaction/EditTransaction?acctgTransId=55700" |
                ['Joe Public', '430000000', 'Customer Service Credits']
        "Accounting/GlAccount/EditGlAccount?glAccountId=110000000" | ['Cash and Equivalent Asset', 'Ziziwork Industries']
        "Accounting/OrgSettings/AcctgPreference?partyId=ORG_ZIZI_RETAIL" |
                ['Ziziwork Industries', 'Clone Accounting Settings']

        // Accounting/Reports
        // NOTE: these are designed to handle account masks of ###-###-### or even down to the 5 digit ###-##
        "Accounting/Reports/BalanceSheet?organizationPartyId=ORG_ZIZI_RETAIL&timePeriodIdList=55100&detail=true" |
                ["Finished Good Inventory", "212-00"]
        "Accounting/Reports/IncomeStatement?organizationPartyId=ORG_ZIZI_RETAIL&timePeriodIdList=55100&detail=true" |
                ["Product Sales", "614-20"]
        "Accounting/Reports/CashFlowStatement?organizationPartyId=ORG_ZIZI_RETAIL&timePeriodIdList=55100&detail=true" |
                ["Finished Good Inventory", "111-10"]
        "Accounting/Reports/RetainedEarningsStatement?organizationPartyId=ORG_ZIZI_RETAIL&timePeriodIdList=55100" |
                ["Net Earnings", "ZIRET F"]
        "Accounting/Reports/FinancialRatios?organizationPartyId=ORG_ZIZI_RETAIL&timePeriodIdList=55100" |
                ["Total Assets", "Accounts Receivable"]

        "Accounting/Reports/PostedAmountSummary?organizationPartyId=ORG_ZIZI_RETAIL&dateRange_poffset=0&dateRange_period=Year" |
                ["Accounts Payable", "Depreciation - Equipment"]
        "Accounting/Reports/PostedBalanceSummary?organizationPartyId=ORG_ZIZI_RETAIL&timePeriodId=55100" |
                ["Customer Service Credits (Discounts and Write-downs)", "Net Income"]

        // Vendor
        "Party/EditParty?partyId=ORG_ZIZI_RETAIL" |
                ['Ziziwork Retail', 'Internal', 'payment.biziwork.retail@test.com']
    }
}
