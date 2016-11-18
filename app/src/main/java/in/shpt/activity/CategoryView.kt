package `in`.shpt.activity

import `in`.shpt.R
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.json.JSONObject

class CategoryView : AppCompatActivity() {

    var categoryId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_view)

        categoryId = intent.getStringExtra("CATEGORYID")
    }

    inner class CategoryProductLoader : AsyncTask<Void, Void, JSONObject>() {
        override fun doInBackground(vararg p0: Void?): JSONObject? {

            return null
        }

        override fun onPostExecute(result: JSONObject?) {
            super.onPostExecute(result)
        }
    }
}
