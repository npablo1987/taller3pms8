package persona.com.taller3pv.data

import com.google.firebase.firestore.FirebaseFirestore

object UserRepository {

    private val db = FirebaseFirestore.getInstance()

    private const val USERS_COLLECTION = "usuarios"
    private const val VOICE_MESSAGES_COLLECTION = "frases_voz"
    fun registerUser(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        db.collection(USERS_COLLECTION)
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { result ->
                if(!result.isEmpty){
                    onError("El correo electrónico ya está registrado")
                }else{
                    val newUser = User(name = name, email = email, password = password)

                    db.collection(USERS_COLLECTION)
                        .add(newUser)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { e -> onError(e.message.toString()) }
                }
            }
            .addOnFailureListener { e -> onError(e.message ?: "Error al registrar el usuario por error de conección!") }
    }

    fun loginUser(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ){
        db.collection(USERS_COLLECTION)
            .whereEqualTo("email", email)
            .whereEqualTo("password", password)
            .get()
            .addOnSuccessListener { result ->
                if(result.isEmpty){
                    onError("Credenciales incorrectas")
                }else{
                   val user = result.documents.first().toObject(User::class.java)
                   onSuccess(user?.name?:"")
                }
            }
            .addOnFailureListener { e -> onError(e.message ?: "error al conectarse con el servidor") }
    }

    fun saveVoiceMessage(
        userName: String,
        text: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        val voiceMessage = VoiceMessage(
            userName = userName,
            text = text,
            timestamp = System.currentTimeMillis()
        )

        db.collection(VOICE_MESSAGES_COLLECTION)
            .add(voiceMessage)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError(e.message ?: "Error al guardar el mensaje por error de conección!") }



    }




}