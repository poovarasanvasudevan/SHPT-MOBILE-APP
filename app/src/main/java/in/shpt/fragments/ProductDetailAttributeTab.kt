package `in`.shpt.fragments

import `in`.shpt.R
import `in`.shpt.adapter.ProductDetailAttributeAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mcxiaoke.koi.ext.find
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by poovarasanv on 29/11/16.
 */

class ProductDetailAttributeTab(var result: JSONObject) : Fragment() {
    lateinit var productAttributeList: RecyclerView
    lateinit var productAttributeListAdapter: FastItemAdapter<ProductDetailAttributeAdapter>
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater?.inflate(R.layout.product_detail_tab_2, container, false)
        productAttributeList = view!!.find<RecyclerView>(R.id.productAttributeList)

        val llm = LinearLayoutManager(activity)
        llm.orientation = LinearLayoutManager.VERTICAL
        productAttributeList.setLayoutManager(llm)


        productAttributeListAdapter = FastItemAdapter()
        productAttributeList.adapter = productAttributeListAdapter


        if (result.optJSONArray("categories").length() > 0) {

            var category: String = ""
            for (i in 0..result.optJSONArray("categories").length() - 1) {
                category += result.optJSONArray("categories").optJSONObject(i).optString("name") + "\n"
            }

            productAttributeListAdapter.add(ProductDetailAttributeAdapter(
                    result.optString("text_category"),
                    category
            ))
        }


        if (result.optJSONArray("attribute_groups").length() > 0) {

            var attr: JSONArray = result.optJSONArray("attribute_groups")
            for (i in 0..attr.length() - 1) {
                for (j in 0..attr.optJSONObject(i).optJSONArray("attribute").length() - 1) {
                    productAttributeListAdapter.add(ProductDetailAttributeAdapter(
                            attr.optJSONObject(i).optJSONArray("attribute").optJSONObject(j).optString("name"),
                            attr.optJSONObject(i).optJSONArray("attribute").optJSONObject(j).optString("text")
                    ))
                }
            }
        }

        productAttributeListAdapter.add(ProductDetailAttributeAdapter(
                result.optString("text_model"),
                result.optString("model")
        ))

        if (result.optString("reward", "NULL") != "NULL" && !result.optString("reward").isBlank() && result.optString("reward", "NULL") != "0" && result.optString("reward", "NULL") != "null") {
            productAttributeListAdapter.add(ProductDetailAttributeAdapter(
                    result.optString("text_reward"),
                    result.optString("reward")
            ))
        }

        if (result.optString("edition", "NULL") != "NULL" && !result.optString("edition").isBlank()) {
            productAttributeListAdapter.add(ProductDetailAttributeAdapter(
                    result.optString("text_edition"),
                    result.optString("edition")
            ))
        }

        if (result.optString("author", "NULL") != "NULL" && !result.optString("author").isBlank()) {
            productAttributeListAdapter.add(ProductDetailAttributeAdapter(
                    result.optString("text_author"),
                    result.optString("author")
            ))
        }

        if (result.optString("item_language", "NULL") != "NULL" && !result.optString("item_language").isBlank()) {
            productAttributeListAdapter.add(ProductDetailAttributeAdapter(
                    result.optString("text_item_language"),
                    result.optString("item_language")
            ))
        }


        if (result.optString("genre", "NULL") != "NULL" && !result.optString("genre").isBlank()) {
            productAttributeListAdapter.add(ProductDetailAttributeAdapter(
                    result.optString("text_genre"),
                    result.optString("genre")
            ))
        }


        if (result.optString("release_year", "NULL") != "NULL" && result.optString("release_year", "NULL") != "0" && !result.optString("release_year").isEmpty()) {
            productAttributeListAdapter.add(ProductDetailAttributeAdapter(
                    result.optString("text_release_year"),
                    result.optString("release_year")
            ))
        }

        if (result.optString("isbn", "NULL") != "NULL" && result.optString("isbn", "NULL") != "0" && !result.optString("isbn").isEmpty()) {
            productAttributeListAdapter.add(ProductDetailAttributeAdapter(
                    result.optString("text_isbn"),
                    result.optString("isbn")
            ))
        }


        if (result.optString("subtitle_languages", "NULL") != "NULL" && result.optString("subtitle_languages", "NULL") != "0"  && !result.optString("subtitle_languages").isEmpty()) {
            productAttributeListAdapter.add(ProductDetailAttributeAdapter(
                    result.optString("text_subtitle_languages"),
                    result.optString("subtitle_languages")
            ))
        }

        if (result.optString("no_of_tracks", "NULL") != "NULL" && result.optString("no_of_tracks", "NULL") != "0" && !result.optString("no_of_tracks").isEmpty()) {
            productAttributeListAdapter.add(ProductDetailAttributeAdapter(
                    result.optString("text_no_of_tracks"),
                    result.optString("no_of_tracks")
            ))
        }

        if (result.optString("no_of_discs", "NULL") != "NULL" && result.optString("no_of_discs", "NULL") != "0" && !result.optString("no_of_discs").isEmpty()) {
            productAttributeListAdapter.add(ProductDetailAttributeAdapter(
                    result.optString("text_no_of_discs"),
                    result.optString("no_of_discs")
            ))
        }



        return view
    }
}
