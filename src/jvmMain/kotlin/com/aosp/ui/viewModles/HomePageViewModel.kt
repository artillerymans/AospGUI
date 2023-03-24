package com.aosp.ui.viewModles

import cn.hutool.core.io.FileUtil
import cn.hutool.core.io.file.FileNameUtil
import cn.hutool.core.io.file.PathUtil
import cn.hutool.core.text.csv.CsvUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class HomePageViewModel: ViewModel() {


    fun readCSVFile(path: String, onSuccess: (String) -> Unit):Boolean{
        viewModelScope.launch(Dispatchers.IO){
            CsvUtil.getReader()
        }
        return FileUtil.isFile(path) && FileNameUtil.getSuffix(path).equals("csv", true)

    }

}