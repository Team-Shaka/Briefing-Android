package store.newsbriefing.app.core.common.util

import android.app.Activity
import android.util.Log
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.AcknowledgePurchaseResponseListener
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams

object InAppUtil : PurchasesUpdatedListener {

    private const val TAG = "GooglePayUtil"

    private lateinit var billingClient: BillingClient
    private var productDetailsList: List<ProductDetails> = mutableListOf()
    private lateinit var acknowledgePurchaseResponseListener: AcknowledgePurchaseResponseListener

    private val productIdList = listOf("premium")

    /**
     * Billing Client 초기화
     * Google Play 연결
     */
    fun initBillingClient(activity: Activity) {
        // Billing Client 초기화
        billingClient = BillingClient.newBuilder(activity)
            .setListener(this)
            .enablePendingPurchases()
            .build()

        // Billing Client 와 Google Play 연결
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                // 연결이 종료될 시 재시도 요망
                Log.d(TAG, "연결 실패")
            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // 연결 성공
                    Log.d(TAG, "연결 성공")
                    Log.d(TAG, "billingClient.connectionState : ${billingClient.connectionState}")
                    queryProductDetails(productIdList)
                }
            }
        })

        acknowledgePurchaseResponseListener = AcknowledgePurchaseResponseListener { billingResult ->
            Log.d(TAG, "billingResult.responseCode : ${billingResult.responseCode}")
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                Log.d(TAG, "비소비성 제품 구매 성공")
            } else {
                Log.d(TAG, "비소비성 제품 구매 실패")
            }
        }
    }

    /**
     * 구매한 상품 조회
     * */
    fun onResume() {
        if (!billingClient.isReady) return

        val params = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.SUBS)

        billingClient.queryPurchasesAsync(params.build()) { _, purchases ->
            Log.d(TAG, "$purchases")

            for (purchase in purchases) {
                handlePurchase(purchase)
            }
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged) {
            // 구독이 활성화된 상태이고 아직 인식되지 않은 경우
            acknowledgePurchase(purchase)
        }
    }

    private fun acknowledgePurchase(purchase: Purchase) {
        val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        billingClient.acknowledgePurchase(acknowledgePurchaseParams) { billingResult ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                Log.d(TAG, "Purchase acknowledged successfully")
            } else {
                Log.e(TAG, "Error acknowledging purchase: ${billingResult.debugMessage}")
            }
        }
    }

    /**
     * 상품 목록 조회
     * */
    fun queryProductDetails(productIds: List<String>) {
        Log.d(TAG, "queryProductDetails")

        val productList = ArrayList<QueryProductDetailsParams.Product>()
        for (id in productIds) {
            productList.add(
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(id)
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build()
            )
        }

        val queryProductDetailsParams =
            QueryProductDetailsParams.newBuilder()
                .setProductList(productList)
                .build()

        billingClient.queryProductDetailsAsync(queryProductDetailsParams) { billingResult, products ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                productDetailsList = products
                Log.d(TAG, "$billingResult")
                Log.d(TAG, "$productDetailsList")
            } else {
                Log.e(TAG, "Error querying product details: ${billingResult.debugMessage}")
            }
        }
    }

    /**
     * 상품 결제 요청
     * */
    fun getPay(activity: Activity, id: String) {
        Log.d(TAG, "getPay : $id")

        val list: MutableList<BillingFlowParams.ProductDetailsParams> = mutableListOf()

        for (productDetails in productDetailsList) {
            if (productDetails.productId == id) {
                val subscriptionOfferDetails = productDetails.subscriptionOfferDetails
                if (subscriptionOfferDetails != null && subscriptionOfferDetails.isNotEmpty()) {
                    val offerToken = subscriptionOfferDetails[0].offerToken

                    val flowProductDetailParams = BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(productDetails)
                        .setOfferToken(offerToken)
                        .build()

                    list.add(flowProductDetailParams)
                }
            }
        }

        val flowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(list)
            .build()

        val responseCode = billingClient.launchBillingFlow(activity, flowParams).responseCode
        Log.d(TAG, responseCode.toString())
        Log.d(TAG, BillingClient.BillingResponseCode.OK.toString())
    }

    /**
     * 결제 결과 수신
     * */
    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                Log.d(TAG, "onPurchasesUpdated : 구매 성공")
                handlePurchase(purchase)
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            Log.d(TAG, "유저 취소")
        } else {
            Log.e(TAG, "Error: ${billingResult.debugMessage}")
        }
    }
}
