package lt.neworld.servicerestarttest

import android.app.Service
import android.content.Intent
import android.os.IBinder
import rx.Observable
import rx.Subscriber
import java.util.concurrent.TimeUnit

/**
 * @author Andrius Semionovas
 * @since 2016-04-04
 */

class TestService : Service() {
    val index: Int = ++INDEX_COUNTER

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        log("Start job")
        startJob()
        return START_NOT_STICKY
    }

    private fun startJob() {
        Observable.zip(
                Observable.range(0, 5),
                Observable.interval(1, TimeUnit.SECONDS),
                { id, timer -> id }
        )
                .subscribe(object : Subscriber<Int>() {
                    override fun onCompleted() {
                        log("completed")
                    }

                    override fun onNext(t: Int) {
                        log(t.toString())
                    }

                    override fun onError(e: Throwable?) {
                        log(e.toString())
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()

        log("Destroy")
    }

    private fun log(message: String) {
        Events.log.onNext("#$index: $message")
    }

    companion object {
        private var INDEX_COUNTER = 0
    }
}