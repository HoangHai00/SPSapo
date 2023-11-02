package com.example.snphmsapo.api.model.datalistvariant

import com.example.snphmsapo.api.model.datalistproduct.VariantData
import com.example.snphmsapo.api.model.datalistproduct.MetadataData

data class ListVariantData(
    val metadata: MetadataData,
    val variants: List<VariantData>
)