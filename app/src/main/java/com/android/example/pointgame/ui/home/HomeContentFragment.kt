package com.android.example.pointgame.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.example.pointgame.R
import com.android.example.pointgame.databinding.FragmentHomeContentBinding
import com.android.example.pointgame.databinding.FragmentHomePagerBinding


class HomeContentFragment(private val position: Int, private val pagerBinding: FragmentHomePagerBinding) : Fragment() {
    private var _binding: FragmentHomeContentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var SUM = 0
    private var pos=0
    private var count = 6

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        val homeContentViewModel =
            ViewModelProvider(this)[HomeContentViewModel::class.java]


        // バインディング
        _binding = FragmentHomeContentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeContentViewModel.startMoveToPointAnimtest(requireActivity(),
            binding.fragmentHomeContentConstraintLayout, binding.fragmentHomeContentTarget, count, pagerBinding)

        // スワイプ位置によって背景画像を動的に変更する
        when (position) {
            0 -> {
                binding.fragmentHomeContentBackground.setImageResource(R.drawable.suica_stamp_rally_background)
                binding.fragmentHomeContentTarget.setImageResource(R.drawable.suica_stamp_rally_train)
                binding.fragmentHomeContentMidIcon.setImageResource(R.drawable.suica_stamp_rally_mid_icon)
                binding.fragmentHomeContentGoalIcon.setImageResource(R.drawable.suica_stamp_rally_goal_icon)
                //binding.fragmentHomeContentPondImage.visibility = View.VISIBLE
            }
            else -> {
                binding.fragmentHomeContentBackground.setImageResource(R.drawable.viewcard_stamp_rally_background)
                binding.fragmentHomeContentTarget.setImageResource(R.drawable.viewcard_stamp_rally_user)
                binding.fragmentHomeContentMidIcon.setImageResource(R.drawable.viewcard_stamp_rally_mid_icon)
                binding.fragmentHomeContentGoalIcon.setImageResource(R.drawable.viewcard_stamp_rally_goal_icon)
                //binding.fragmentHomeContentPondImage.visibility = View.GONE
            }
        }

        // Moveボタン押下した際の挙動
        binding.fragmentHomeContentMoveButton.setOnClickListener{
            val money = arrayOf(1000,1000,1000).random() //追加分

            SUM = SUM + money

            DataSave()

            var n = (money-money%500)/500 //追加分
            while(n>0){
                // ゴールに到達していない場合
                if (count < homeContentViewModel.translations.size) {
                    count++
                    homeContentViewModel.startMoveToPointAnim(requireActivity(),
                        binding.fragmentHomeContentConstraintLayout, binding.fragmentHomeContentTarget,
                        count, pagerBinding)
                }
                n = n-1
            }
        }

//        // マスの座標位置調査用
//        root.setOnTouchListener { _, event ->
//            when(event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    Log.v("ACTION_DOWN", "X:${event.x.toInt()}, Y:${event.y.toInt()}")
//                }
//                MotionEvent.ACTION_UP -> {
//                    Log.v("ACTION_UP", "X:${event.x.toInt()}, Y:${event.y.toInt()}")
//                }
//            }
//            true
//        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun DataSave(){

        requireContext().openFileOutput("SUM", AppCompatActivity.MODE_PRIVATE).bufferedWriter().use{
            it.write("mieno")
        }
        requireContext().openFileOutput("position", AppCompatActivity.MODE_PRIVATE).bufferedWriter().use{
            it.write("xiao")
        }
    }



}