package rickykuang.com.homies

import android.app.Application
import com.google.firebase.auth.FirebaseAuth

class App : Application() {
    companion object {
        lateinit var mAuth: FirebaseAuth
    }
}