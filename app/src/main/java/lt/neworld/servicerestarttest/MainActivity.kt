package lt.neworld.servicerestarttest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers

class MainActivity : AppCompatActivity() {
    private lateinit var logSubscription: Subscription
    private val adapter: ArrayAdapter<String> by lazy {
        ArrayAdapter<String>(this, android.R.layout.test_list_item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        log.adapter = adapter
        restart.setOnClickListener { restartService() }
        logSubscription = Events.log
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { log(it) }
    }

    override fun onDestroy() {
        super.onDestroy()

        logSubscription.unsubscribe()
    }

    fun restartService() {
        val intent = Intent(this, TestService::class.java)
        stopService(intent)
        startService(intent)
    }

    fun log(line: String) {
        adapter.add(line)
    }
}
