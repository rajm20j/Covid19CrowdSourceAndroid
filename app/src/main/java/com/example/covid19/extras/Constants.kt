package com.example.covid19.extras

object Constants {
    const val BASE_URL: String = "https://api.covid19india.org/"
    val homeListItems = mutableListOf<String>("Data", "State/District wise", "Travel history", "Raw data")
    val India: String? = "TT"
    val states = mapOf<String, String> (
            "AP" to "Andhra Pradesh",
            "AR" to "Arunachal Pradesh",
            "AS" to "Assam",
            "BR" to "Bihar",
            "CT" to "Chhattisgarh",
            "GA" to "Goa",
            "GJ" to "Gujarat",
            "HR" to "Haryana",
            "HP" to "Himachal Pradesh",
            "JH" to "Jharkhand",
            "KA" to "Karnataka",
            "KL" to "Kerala",
            "MP" to "Madhya Pradesh",
            "MH" to "Maharashtra",
            "MN" to "Manipur",
            "ML" to "Meghalaya",
            "MZ" to "Mizoram",
            "NL" to "Nagaland",
            "OR" to "Odisha",
            "PB" to "Punjab",
            "RJ" to "Rajasthan",
            "SK" to "Sikkim",
            "TN" to "Tamil Nadu",
            "TG" to "Telangana",
            "TR" to "Tripura",
            "UT" to "Uttarakhand",
            "UP" to "Uttar Pradesh",
            "WB" to "West Bengal",
            "AN" to "Andaman and Nicobar Islands",
            "CH" to "Chandigarh",
            "DN" to "Dadra and Nagar Haveli and Daman and Diu",
            "DL" to "Delhi",
            "JK" to "Jammu and Kashmir",
            "LA" to "Ladakh",
            "LD" to "Lakshadweep",
            "PY" to "Puducherry",
            "TT" to "India"
    )
}