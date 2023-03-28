package com.aosp.ui.viewModles

import cn.hutool.core.io.FileUtil
import cn.hutool.core.io.file.FileNameUtil
import cn.hutool.core.text.csv.CsvUtil
import com.alibaba.fastjson2.toJSONString
import com.aosp.base.BaseAction
import com.aosp.exts.*
import com.aosp.ui.presenters.HomePageAction
import com.aosp.ui.presenters.HomePageState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import java.io.IOException
import java.nio.charset.Charset

class HomePageViewModel : ViewModel() {

    private val androidxMap: HashMap<String, String> by lazy(LazyThreadSafetyMode.NONE) {
        HashMap()
    }

    fun readCSVFile(
        bus: Channel<BaseAction>,
        path: String
    ) {
        val status = FileUtil.isFile(path) && FileNameUtil.getSuffix(path).equals("csv", true)
        if (status) {
            bus.post(HomePageAction.LoadCSVFile(path))
            var isError = false
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    bus.loading()
                    val reader = CsvUtil.getReader()
                    val data = reader.read(FileUtil.file(path), Charset.defaultCharset())
                    if (androidxMap.size > 0){
                        androidxMap.clear()
                    }
                    data.rows.forEach {
                        if (it.rawList.isNullOrEmpty()) {
                            return@forEach
                        }
                        if (it.rawList.size >= 2) {
                            androidxMap[it.rawList[0]] = it.rawList[1]
                        }
                        bus.print(it.rawList.toJSONString())
                    }
                    bus.success()
                } catch (e: IOException) {
                    e.printStackTrace()
                    isError = true
                    bus.errorAndLog("解析过程中发生错误")
                } finally {
                    bus.loadFinish()
                }
            }
        }else {
            bus.errorAndLog("请选择CSV文件")
        }
    }


    fun start(state: HomePageState, bus: Channel<BaseAction>){
        viewModelScope.launch(Dispatchers.IO){
            bus.loading()

            state.run {
                if (path.isEmpty()){
                    return@run
                }

                val listFiles = FileUtil.loopFiles(path)
                listFiles.forEach {
                    bus.print(FileUtil.getAbsolutePath(it))
                }
            }

            bus.loadFinish()
        }
    }

}



