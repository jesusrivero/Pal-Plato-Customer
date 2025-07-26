package com.techcode.palplato.presentation.ui.bussines

data class CategoryWithProducts(
	val name: String,
	val products: List<String>
)

// En tu carpeta data o utils
val categoryProductList = listOf(
	CategoryWithProducts("Hamburguesas", listOf("Clásicas", "Dobles", "Con tocineta", "Veganas", "Con queso cheddar", "Con huevo", "Picantes")),
	CategoryWithProducts("Pizza", listOf("A la leña", "Pepperoni", "Hawaiana", "Cuatro quesos", "Margarita", "Vegetariana", "Barbacoa")),
	CategoryWithProducts("Sushi", listOf("California roll", "Nigiri", "Tempura roll", "Sashimi", "Uramaki", "Maki", "Dragon roll")),
	CategoryWithProducts("Comida Mexicana", listOf("Tacos", "Burritos", "Quesadillas", "Nachos", "Fajitas", "Chilaquiles", "Enchiladas")),
	CategoryWithProducts("Comida China", listOf("Arroz frito", "Pollo agridulce", "Chop suey", "Rollitos primavera", "Cerdo con vegetales", "Pato pekinés")),
	CategoryWithProducts("Comida Italiana", listOf("Lasagna", "Raviolis", "Fettuccine Alfredo", "Bruschetta", "Risotto", "Pizza napolitana", "Cannoli")),
	CategoryWithProducts("Comida Árabe", listOf("Shawarma", "Kibbe", "Tabule", "Hummus", "Falafel", "Pan pita", "Arroz con cordero")),
	CategoryWithProducts("Postres", listOf("Torta de chocolate", "Cheesecake", "Brownies", "Tres leches", "Galletas", "Helado frito", "Mousse de maracuyá")),
	CategoryWithProducts("Bebidas", listOf("Jugos", "Gaseosas", "Smoothies", "Café", "Té helado", "Malteadas", "Limonadas")),
	CategoryWithProducts("Helados", listOf("Chocolate", "Vainilla", "Fresa", "Arequipe", "Menta con chispas", "Cookies and cream", "Pistacho")),
	CategoryWithProducts("Ensaladas", listOf("César", "Mixta", "De frutas", "Con pollo", "Caprese", "Griega", "Quinoa")),
	CategoryWithProducts("Pastas", listOf("Spaghetti boloñesa", "Macarrones con queso", "Pasta al pesto", "Tallarines", "Pasta carbonara", "Ñoquis", "Pasta alfredo")),
	CategoryWithProducts("Pollo", listOf("A la brasa", "Frito", "A la plancha", "Con champiñones", "Empanizado", "Al curry", "Pollo teriyaki")),
	CategoryWithProducts("Carnes", listOf("Lomo a la parrilla", "Churrasco", "Costillas BBQ", "Asado negro", "Carne mechada", "Filete migñón", "Ropa vieja")),
	CategoryWithProducts("Desayunos", listOf("Arepas", "Huevos revueltos", "Panquecas", "Sandwiches", "Cachapas", "Empanadas", "Tostadas francesas")),
	CategoryWithProducts("Mariscos", listOf("Camarones al ajillo", "Paella", "Pulpo a la gallega", "Ceviche", "Langosta", "Mejillones")),
	CategoryWithProducts("Empanadas", listOf("Carne molida", "Pollo", "Queso", "Pabellón", "Cazón", "Jamon y queso")),
	CategoryWithProducts("Sandwiches", listOf("Club", "Mixto", "Vegetariano", "De pollo", "BLT", "De roast beef")),
	CategoryWithProducts("Snacks", listOf("Papas fritas", "Tequeños", "Alitas", "Tostones", "Churros", "Dedos de queso")),
	CategoryWithProducts("Vegetariano", listOf("Hamburguesas veganas", "Ensaladas verdes", "Wraps vegetales", "Sopa de lentejas", "Tofu grillado")),
	CategoryWithProducts("Comida Venezolana", listOf("Pabellón criollo", "Hallacas", "Arepas rellenas", "Cachapas", "Asado negro")),
	CategoryWithProducts("Comida Peruana", listOf("Ceviche", "Lomo saltado", "Ají de gallina", "Anticuchos", "Papa a la huancaína")),
	CategoryWithProducts("Comida Japonesa", listOf("Ramen", "Tonkatsu", "Sushi variado", "Tempura", "Yakimeshi")),
	CategoryWithProducts("Comida India", listOf("Pollo tikka masala", "Samosas", "Naan", "Curry de lentejas", "Arroz biryani"))
)
