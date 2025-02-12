# **REFLECTION 1**

Dalam tugas ini, beberapa prinsip *clean code* telah diterapkan:

### Meaningful Naming
Semua variabel, metode, dan class menggunakan nama yang deskriptif, seperti `ProductController`, `ProductService`, dan `productName` sehingga lebih mudah dipahami.

### Single Responsibility Principle (SRP)
- `ProductController` menangani request dan mengarahkan ke *view*.
- `ProductService` bertanggung jawab atas *business logic*.
- `ProductRepository` menyimpan dan mengambil data.

### Avoiding Hardcoded Values
Semua data produk menggunakan model `Product` yang membuat sistem lebih fleksibel dibandingkan menulis nilai langsung dalam kode.

---

## **Reflection & Code Improvement**

Saya menyadari bahwa dalam implementasi awal, saya belum menambahkan validasi input (error handling) untuk memastikan data yang dimasukkan pengguna valid sebelum disimpan ke database. 


Saya juga tidak menambahkan komen pada kode karena kode dapat dipahami.

Kemudian, untuk kode create saya menambahkan UUID set product agar pada saat product dibuat, `stringId` akan memiliki value. Saya mendapati error jika tidak menambahkan UUID karena product tidak menyimpan ID.

```
    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return  "createProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model) {
        product.setProductId(UUID.randomUUID().toString());
        service.create(product);
        return "redirect:list";
    }
```