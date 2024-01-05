package com.example.Lab_Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.Lab_Fragment.databinding.FragmentSecondBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//이것도 먼저 선언해줘야함.
private const val ARG_PARAM1 = "param1"


/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {

    //[3] SecondFragment -> Activity (프레그먼트에서 액티비티로 데이터 넘길 때 선언해줘야함.)
    private var listener: FragmentDataListener? = null

    // 변수를 먼저 선언해줘야함.
    private var param1: String? = null

    private val binding by lazy { FragmentSecondBinding.inflate(layoutInflater) }

    //메인액티비티랑 세컨드프레그먼트를 연결해주는 거임.
    //context(<-메인액티비티에서 온 것)를 통해 파라미터가 들어오는 것임
    override fun onAttach(context: Context) {
        super.onAttach(context)

        // [3] SecondFragment -> Activity
        // is를 통해 타입체크(프레그먼트데이터리스너가 구현되어있냐를 체크 = 메인액티비티안에 프레그먼트데이터리스너가 있냐는 뜻)
        if (context is FragmentDataListener) {
            //구현체가 있다는 것을 true로 확인 받으면 listener에 context를 넣는 것임.
            // 이 리스너를 이용해서  onViewCreated에서 호출.
            listener = context
        } else {
            throw RuntimeException("$context must implement FragmentDataListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
        //return inflater.inflate(R.layout.fragment_second, container, false)
    }

    //내가 붙여준것.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // [2] Fragment -> Fragment
        binding.tvFrag2Text.text = param1

        // [3] SecondFragment -> Activity
        binding.btnSendToActivity.setOnClickListener{
            val dataToSend = "Hello from SecondFragment!"
            listener?.onDataReceived(dataToSend)
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FirstFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            // [1] Activity -> FirstFragment
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}