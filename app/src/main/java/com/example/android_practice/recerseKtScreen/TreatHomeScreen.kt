package com.example.android_practice.recerseKtScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.SnapHelper
import com.example.android_practice.R
import com.example.android_practice.databinding.ActivityTreatHomeScreenBinding
import com.example.android_practice.databinding.DetailCardTodayActivitiesBinding
import com.example.android_practice.databinding.WorkoutDetailCardBinding
import com.example.android_practice.recerseKtScreen.adapter.ClickHandle
import com.example.android_practice.recerseKtScreen.adapter.MedicineAdapter
import com.example.android_practice.recerseKtScreen.adapter.SysCardAdapter
import com.example.android_practice.recerseKtScreen.dataModel.Helper
import com.example.android_practice.recerseKtScreen.dataModel.MedicineData
import com.example.android_practice.recerseKtScreen.dataModel.SysData
import com.example.android_practice.recerseKtScreen.dataModel.WorkOutData
import java.time.chrono.HijrahEra

class TreatHomeScreen : AppCompatActivity(), ClickHandle {

    private lateinit var binding: ActivityTreatHomeScreenBinding

    private lateinit var medicineCardBinding: ArrayList<DetailCardTodayActivitiesBinding>
    private lateinit var glucoseDetailList: ArrayList<WorkoutDetailCardBinding>

    private lateinit var sysDataList: ArrayList<SysData>
    private lateinit var sysAdapter: SysCardAdapter

    private lateinit var medicineDataList: ArrayList<MedicineData>
    private lateinit var medicineAdapter: MedicineAdapter

    private lateinit var snapHelper: SnapHelper

    private var isSelectedLike = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTreatHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getGlucoseDetailBinding()
        setDataToGlucoseDetail()
        sysSetDataSet()
        setCompoundData()
        setMedicineData()

        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvSysCard)

//        binding.rvSysCard.ibHeart.setOnClickListener {
//            if (isSelectedLike) {
//                binding.sysCard.ibHeart.setImageResource(R.drawable.heart)
//            } else {
//                binding.sysCard.ibHeart.setImageResource(R.drawable.fill_heart)
//            }
//            isSelectedLike = !isSelectedLike
//        }
//
//        binding.diaCard.ibHeart.setOnClickListener {
//            if (isSelectedLike) {
//                binding.diaCard.ibHeart.setImageResource(R.drawable.heart)
//            } else {
//                binding.diaCard.ibHeart.setImageResource(R.drawable.fill_heart)
//            }
//            isSelectedLike = !isSelectedLike
//        }

        binding.glucoseCard.root.setOnClickListener {
            val intent = Intent(this, TreatScreen::class.java)
            startActivity(intent)
        }
    }

    private fun getGlucoseDetailBinding() {
        glucoseDetailList = arrayListOf(binding.glucoseCard.cardCalories, binding.glucoseCard.cardRunDuration, binding.glucoseCard.cardSpeed)
    }

    private fun setDataToGlucoseDetail() {
        glucoseDetailList.forEachIndexed { position,it ->
            it.workOutData = Helper.getDataOfGlucoseDetail(this)[position]
        }
    }

    private fun sysSetDataSet() {
        sysDataList = Helper.getSystolicCardData()
        binding.rvSysCard.layoutManager = GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)
        sysAdapter = SysCardAdapter(this)
        binding.rvSysCard.adapter = sysAdapter
        sysAdapter.submitList(sysDataList)
    }


    private fun setMedicineData() {
        medicineDataList = Helper.getMedicineData(this)
        binding.rvMedicineData.layoutManager = LinearLayoutManager(this)
        medicineAdapter = MedicineAdapter()
        binding.rvMedicineData.adapter = medicineAdapter
        medicineAdapter.submitList(medicineDataList)
    }

    private fun setCompoundData() {
        binding.glucoseCardData = Helper.getCompoundsData()
    }

    private fun onLikedClick(position: Int) {
        sysDataList[position].isLiked = !sysDataList[position].isLiked
        sysAdapter.submitList(sysDataList)
        sysAdapter.notifyItemChanged(position, Helper.PayloadConstants.LikedButtonChanged)
    }

    override fun onLikedClickInterface(position: Int) {
        sysDataList[position].isLiked = !sysDataList[position].isLiked
        sysAdapter.submitList(sysDataList)
        sysAdapter.notifyItemChanged(position, Helper.PayloadConstants.LikedButtonChanged)
    }

}