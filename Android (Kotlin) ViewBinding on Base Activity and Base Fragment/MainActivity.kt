import android.os.Bundle
import com.domainname.app.R
import com.domainname.app.base.BaseActivity
import com.domainname.app.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun activityInflateBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel(vm)
    }
}