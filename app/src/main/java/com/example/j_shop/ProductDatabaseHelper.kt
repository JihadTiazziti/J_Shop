import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.j_shop.Product
import com.example.j_shop.R

class ProductDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "products.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        // Création de la table
        db.execSQL(
            """
            CREATE TABLE products (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                price REAL NOT NULL,
                imageResId INTEGER NOT NULL
            )
            """.trimIndent()
        )

        // Insertion de produits initiaux
        insertInitialProducts(db)
    }

    private fun insertInitialProducts(db: SQLiteDatabase) {
        val initialProducts = listOf(
            Product(0, "Sneakers", 49.99, R.drawable.img1),
            Product(0, "Watch", 79.99, R.drawable.img2),
            Product(0, "Headphones", 29.99, R.drawable.img3),
            Product(0, "T-shirt", 19.99, R.drawable.img4),
            Product(0, "Backpack", 39.99, R.drawable.img5),
            Product(0, "Sunglasses", 24.99, R.drawable.img6),
            Product(0, "Laptop", 599.99, R.drawable.img7),
            Product(0, "Smartphone", 499.99, R.drawable.img8),
            Product(0, "Camera", 299.99, R.drawable.img9),
            Product(0, "Hoodie", 34.99, R.drawable.img10),
            Product(0, "Shoes", 59.99, R.drawable.img11),
            Product(0, "Cap", 14.99, R.drawable.img12)
        )

        for (product in initialProducts) {
            val values = ContentValues().apply {
                put("name", product.name)
                put("price", product.price)
                put("imageResId", product.imageResId)
            }
            db.insert("products", null, values)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Réinitialise la base si on change de version
        db.execSQL("DROP TABLE IF EXISTS products")
        onCreate(db)
    }

    // Récupérer tous les produits
    fun getAllProducts(): List<Product> {
        val productList = mutableListOf<Product>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM products", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"))
                val imageResId = cursor.getInt(cursor.getColumnIndexOrThrow("imageResId"))
                productList.add(Product(id, name, price, imageResId))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return productList
    }

    // Ajouter dynamiquement un produit
    fun addProduct(product: Product): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", product.name)
            put("price", product.price)
            put("imageResId", product.imageResId)
        }
        return db.insert("products", null, values)
    }
}
