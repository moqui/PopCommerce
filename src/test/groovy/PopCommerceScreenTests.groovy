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

class PopCommerceScreenTests extends Specification {
    protected final static Logger logger = LoggerFactory.getLogger(PopCommerceScreenTests.class)

    @Shared
    ExecutionContext ec
    @Shared
    ScreenTest screenTest
    @Shared
    long effectiveTime = System.currentTimeMillis()

    def setupSpec() {
        ec = Moqui.getExecutionContext()
        ec.user.loginUser("joe@public.com", "moqui")
        logger.info("Logged in customer user ${ec.user.userId}")
        screenTest = ec.screen.makeTest().baseScreenPath("popc")

        ec.entity.tempSetSequencedIdPrimary("mantle.order.OrderHeader", 63200, 10)
        ec.entity.tempSetSequencedIdPrimary("mantle.work.effort.WorkEffort", 63200, 10)
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

    @Unroll
    def "render POP Commerce screen (#screenPath, #containsTextList)"() {
        setup:
        ScreenTestRender str = screenTest.render(screenPath, null, null)
        // logger.info("Rendered ${screenPath} in ${str.getRenderTime()}ms, output:\n${str.output}")
        boolean containsAll = true
        for (String containsText in containsTextList) {
            boolean contains = containsText ? str.assertContains(containsText) : true
            if (!contains) {
                logger.warn("In ${screenPath} text [${containsText}] not found:\n${str.output}")
                containsAll = false
            }

        }

        expect:
        !str.errorMessages
        containsAll

        where:
        screenPath | containsTextList
        // Home and Product
        "Home" | ['Search products', 'Demo Product One-Two']
        "Product/Detail/DEMO_1_1/Demo-Product-One-One" | ['Demo Product One-One', '$16.99', 'Shipping Weight']
        "Product/Category/DEMO_ONE" | ['Demo Product One Unit', '$1.00', 'Add to Cart']
        // NOTE: DEJ 20201223 commented for now, no elasticsearch running on Moqui Travis setup
        // "Product/Search?keywords=demo" | ['Demo Product One Unit', '$1.00', 'Demo Category One']

        // Customer
        "Customer/Profile" | ['joe@public.com', 'Visa ************1111', '1350 E. Flamingo Rd. #2345']
        "Customer/OrderHistory" | ['Completed', 'Placed', '55401']
        "Customer/OrderDetail?orderId=55401" | ['$9,000.00', '1-702-234-5678', 'Picker Bot 2000']

        // Checkout
        "Home/addToCart?productId=DEMO_1_1&quantity=10" | []
        "Order/Cart" | ['Demo Product One-One', '$16.99']
        "Order/Checkout" | ['Visa ************1111', '1350 E. Flamingo Rd. #2345', 'Ground Parcel']
        "Order/Checkout/setOrderBillingShippingInfo?paymentMethodId=CustJqpCc&shippingPostalContactMechId=CustJqpAddr&carrierAndShipmentMethod=_NA_:ShMthGround" | []
        "Order/Review" | ['Joe Q. Public', 'Demo Product One-One', 'Order Total: $']
        "Order/Review/placeOrder" | []
        "Customer/OrderDetail?orderId=63200" | ['$16', '1-702-234-5678', 'Demo Product One-One', 'Ground Parcel']
    }
}
