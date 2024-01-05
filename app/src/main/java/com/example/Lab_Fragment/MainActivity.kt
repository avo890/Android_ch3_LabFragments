package com.example.Lab_Fragment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.Lab_Fragment.databinding.ActivityMainBinding

class MainActivity() : AppCompatActivity(), FragmentDataListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // [1] Activity -> FirstFragment 데이터 전송 보내기. <-어렵지않다.
        binding.run {
            fragment1Btn.setOnClickListener {
                
                //전송할데이터 선언. 이번예제에서는 String데이터이다.
                val dataToSend = "Hello First Fragment! \n From Activity"
                
                //FistFragment클래스안에 newInstance함수(프레그먼트클래스만들면 자동으로 있음)가 있어야함.
                //dataToSend가 newInstance함수의 생성자 param1 인것임.ㅇㅇ
                val fragment = FirstFragment.newInstance(dataToSend)
                
                //FirstFragment( )를 담는게 아니라 보낼데이터도 newInstance함수에 같이 담아서 변수에 담아서 그 변수를 setFragment해주는 것.
                setFragment(fragment)
            }

            fragment2Btn.setOnClickListener {
                val dataToSend = "Hello Second Fragment!\n From Activity"
                val fragment = SecondFragment.newInstance(dataToSend)
                setFragment(fragment)
            }
        }

        //아무것도 안눌렀어도 첫번째 프레그먼트가 나올수 있도록 기본값 세팅한 것.
        setFragment(FirstFragment())
    }

    private fun setFragment(frag: Fragment) {
        //프레그먼트ktx 의존성을 추가해야지 사용가능
        //사용자 상호작용에 응답해 Fragment를 추가하거나 삭제하는등 작업을 할 수 있게 해주는 매니저
        supportFragmentManager.commit {
            //어느프레임 레이아웃에 띄울것이냐, 어떤프래그먼트냐
            replace(R.id.frameLayout, frag)
            //애니메이션과 전환이 올바르게 작동하도록 트랜잭션과 관련된 프래그먼트의 상태 변경을 최적화
            setReorderingAllowed(true)
            //뒤로가기 버튼 클릭시 다음 액션 (이전 fragment로 가거나 앱이 종료되거나)
            addToBackStack("")
        }
    }

    // [3] SecondFragment -> Activity
    // 어쩃든 토스트는 메인액티비티가 띄우는 것이다.
    override fun onDataReceived(data: String) {
        // Fragment에서 받은 데이터를 처리 (=인터페이스 상속받고 실제 구현체는 여기에 작성한 것임)
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show()
    }
}