package com.example.mycity.data

import com.example.mycity.model.Category
import com.example.mycity.model.Recommendation
import com.example.mycity.R

class RecommendationRepository {

    // Статичные данные; в реальном приложении можно заменить на API/БД
    private val categories = listOf(
        Category("cafes", "Кофейни"),
        Category("parks", "Парки"),
        Category("museums", "Музеи")
    )

        /*private val recommendations = listOf(
        Recommendation("cafes", "Кофейня «Ирис»"),
        Recommendation("cafes", "Кофейня «Латте Арт»"),
        Recommendation("cafes", "Кофейня «Грин»"),
        Recommendation("parks", "Центральный парк"),
        Recommendation("parks", "Парк «Солнечный»"),
        Recommendation("parks", "Сад памяти"),
        Recommendation("museums", "Исторический музей"),
        Recommendation("museums", "Музей современного искусства"),
        Recommendation("museums", "Дом-музей писателя")
    )
    */
        private val recommendations = listOf(
            Recommendation("cafes", "Кафе «Ayami»",
                "Вкусные роллы и суши с доставкой в Ижевске от «АЯМИ»",
                R.drawable.cafe_ayami),
            Recommendation("cafes", "Кафе «Pasta Pizza»",
                "Pasta | Уютные рестораны для всей семьи+доставка",
                R.drawable.cafe_pasta),
            Recommendation("cafes", "Кафе «Ялта»",
                "Для того, чтобы наслаждаться курортным отдыхом, не нужен чемодан и билет на самолет!",
                R.drawable.cafe_yalta),

            Recommendation("museums", "Музей Калашникова",
                "Экспозиции музея посвящены биографии Калашникова, истории оружейного производства, образцам исторического и современного оружия.",
                R.drawable.museum_kalashnikov),
            Recommendation("museums", "Музей Мототехники",
                "Ижевский мотомузей Кожушковых — первый в Ижевске частный музей мотоциклов и мототехники.",
                R.drawable.museum_moto),
            Recommendation("museums", "Национальный музей Удмуртской Республики",
                "Все о природе, истории, культуре Удмуртии за 10 тысяч лет в экспозиции, выставках, коллекциях и мероприятиях музея.",
                R.drawable.museum_national),

            Recommendation("parks", "Парк Горького",
                "О парке\n" +
                        "Парк Горького – одно из самых известных и старейших общественных пространств Ижевска. Это место сосредоточения городских аттракционов, здесь проходят масштабные мероприятия, праздники и аутентичные фестивали.",
                R.drawable.park_gorkii),
            Recommendation("parks", "Парк Кирова",
                "Парк Кирова – самая большая в Ижевске лесопарковая территория. Это место для прогулок, велоспорта, пробежек. Зимой тут организованы каток и лыжная трасса, благодаря сохраненному рельефу местности, здесь регулярно проходят спортивные состязания по скандинавской ходьбе.",
                R.drawable.park_kirova),
            Recommendation("parks", "Козий Парк",
                "«Козий парк» — это парк культуры и отдыха, расположенный в центре города. Он был создан на месте бывшего оврага, который был облагорожен и превращен в современную интерактивную площадку.",
                R.drawable.park_kozii),
        )

    fun getCategories(): List<Category> = categories

    fun getRecommendationsFor(categoryName: String): List<Recommendation> =
        recommendations.filter { it.categoryName == categoryName }
}
