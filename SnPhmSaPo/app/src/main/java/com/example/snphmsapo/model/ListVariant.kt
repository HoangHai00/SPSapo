package com.example.snphmsapo.model

data class ListVariant(
    var metadata: MetaData? = null,
    var variants: List<Variant>? = null
) {
    companion object {
        fun getListVariant(
            apiVariantProduct: com.example.snphmsapo.api.model.datalistvariant.ListVariantData
        ): ListVariant {
            return ListVariant(
                metadata = MetaData.getMetaDataVariant(apiVariantProduct.metadata),
                variants = Variant.getVariant(apiVariantProduct.variants)
            )
        }
    }


}

