package com.example.snphmsapo.model

class VariantDetail(
    val variant: Variant
) {
    companion object {
        fun getVariantDetail(apiVariantDetail: com.example.snphmsapo.api.model.datavariantdetail.VariantDetailData): VariantDetail {
            return VariantDetail(
                variant = Variant.getVariantDetail(apiVariantDetail.variant)
            )
        }
    }
}

