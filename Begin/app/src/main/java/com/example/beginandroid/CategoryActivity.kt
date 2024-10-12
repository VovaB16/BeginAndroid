package com.example.beginandroid

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beginandroid.R
import com.example.beginandroid.category.CategoriesAdapter
import com.example.beginandroid.dto.CategoryItemDTO
import com.example.beginandroid.services.ApplicationNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryActivity : BaseActivity() {
    private val rcCategories: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.rcCategories)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_category)

        // Обробка віконних вставок для налаштування padding
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById<View>(R.id.main)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Налаштовуємо RecyclerView
        rcCategories.setHasFixedSize(true)
        rcCategories.layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)

        // Завантажуємо список
        loadList()
    }

    // Метод для завантаження списку з API
    fun loadList() {
        ApplicationNetwork.getInstance().categoriesApi.list()
            .enqueue(object : Callback<List<CategoryItemDTO?>?> {
                override fun onResponse(
                    call: Call<List<CategoryItemDTO?>?>,
                    response: Response<List<CategoryItemDTO?>?>
                ) {
                    val items = response.body() ?: return
                    val ca = CategoriesAdapter(items)
                    rcCategories.adapter = ca
                }

                override fun onFailure(call: Call<List<CategoryItemDTO?>?>, throwable: Throwable) {
                    // Обробка помилки
                }
            })
    }
}
