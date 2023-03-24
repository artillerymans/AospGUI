package com.aosp.ui.viewModles

import cn.hutool.core.text.csv.CsvUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class HomePageViewModel: ViewModel() {


    fun readCSVFile(path: String){
        viewModelScope.launch(Dispatchers.IO){
            CsvUtil.getReader()
        }
    }

}