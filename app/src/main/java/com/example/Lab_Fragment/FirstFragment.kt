package com.example.Lab_Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.Lab_Fragment.databinding.FragmentFirstBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//이것도 먼저 선언해줘야함.
private const val ARG_PARAM1 = "param1"


/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {

    // 변수를 먼저 선언해줘야함. 받아오는 값을 담기 위한 그릇
    private var param1: String? = null

    //fragment_first랑 연결도 해줘야 함.
    private val binding by lazy { FragmentFirstBinding.inflate(layoutInflater) }

    companion object { //newInstance로 데이터를 받아와서 Bundle안에 putString으로 받은 값을 저장.
        @JvmStatic
        fun newInstance(param1: String) =
            // [1] Activity -> FirstFragment
            FirstFragment().apply {
                //여기 번들에 putString으로 dataToSend를 넣는 것.
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //null 방지로 사용하는 스코프함수 let
        arguments?.let {
            //onCreate될때 번들에서 put해놓은 것을 getString하는 것.
            // 지금 이 param1에 put해놓은 값을 getString해서 위에 그릇으로 선언한 전역변수 param1에 사용하려고 담는 것.
            param1 = it.getString(ARG_PARAM1)
        }
    }

    //Fragment가 처음 생성될 떄 Fragment에 필요한 View들을 생성하여 반환
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_first, container, false)
        // 반환값을 binding.root를 줘야지 밑에 onViewCreated가 먹힌다.
        // 위에처럼 return inflater.~를 하면! 말그대로 R.layout.fragment_first가 출력된다.
        // 대신에 매인엑티비티에 기본값으로  setFragment(FirstFragment())를 해놨는데, 이땐 Frag1버튼을 누르기 전이니까
        //데이터가 안넘어가서 param1 = null이니까 아무것도 안뜬다. null에 다른값을 설정하면 그게 뜬다.
        return binding.root


    }

    //Fragment의 View가 완전히 생성된 후 View와 관련된 초기화 코드 작성.
    //내가 붙여준것.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // [1] Activity -> FirstFragment 보내준 데이터 받아서 사용
        // onCreate에서 param1에 getString한 값(=dataToSend)을 setText한 것.
        binding.tvFrag1Text.text = param1


        // [2] Fragment -> Fragment
        binding.btnGoFrag2.setOnClickListener {
            val dataToSend = "Hello Fragment2! \n From Fragment1"
            val fragment2 = SecondFragment.newInstance(dataToSend)

            // 프래그먼트에서 프래그먼트로 교체할 때 사용되는 함수
            // 어쨌든 Replace에서 fragment2로 똑같이 dataTosend랑 같이 newInstance로 보내줬으니까 여기서부턴 [1]이랑 같은 개념으로
            //SecondFragment에서 데이터 받아서 사용.
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment2)
                .addToBackStack(null)
                .commit()
        }

    }


}