package com.example.pedro.usuariosfirebase;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*
HE TENIDO QUE AÑADIR ESTO EN EL BUILD.GRADLE DE LA APP
¡¡QUE NO SE TE OLVIDE PARA LA PROXIMA VEZ PARA INCLUIR ALGUN SERVICIO DE FIREBASE!!
    compile 'com.google.firebase:firebase-auth:9.6.1'
 */

public class MainActivity extends AppCompatActivity {
    public FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ESTO HAY QUE PONERLO AQUÍ PORQUE SI NO EL ONSTART DA UN CRASH +++++++++++++++++++++++

        //(1)En el método de tu actividad de inicio de sesiónonCreate, obtén la instancia compartida del objeto FirebaseAuth:
        mAuth = FirebaseAuth.getInstance();

        //(1) FIN

        // (2) Configura unAuthStateListener que responda a los cambios en el estado de inicio de sesión del usuario:

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(MainActivity.this, "se ha logado "+ user.toString(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


// (2) FIN ++++++++++++++++++++++++++++++++++++

        // -------- BOTON Y CAMPOS DE TEXTO
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText password = (EditText) findViewById(R.id.password);
        Button autentica = (Button) (Button) findViewById(R.id.autentica);
        autentica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailTexto = email.getText().toString();
                final String passwordTexto = password.getText().toString();
                autenticaUsuario(emailTexto,passwordTexto); // autenticamos el usuario
               // Log.d("OnClick", "email=" + emailTexto + " password=" + passwordTexto);

            }
        });

        Button obtenperfil = (Button) (Button) findViewById(R.id.obtenperfil);
        obtenperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = "";
                String nombre = "";
                Uri photoUri = null;
                obtenPerfilUsuario(nombre,email,photoUri); //obtenemos perfil del usuario


                // Log.d("OnClick", "email=" + emailTexto + " password=" + passwordTexto);

            }
        });
        //-----------


            }


    @Override
    public void onStart() {
        super.onStart();
        //esta parte es también de la gestión de usuarios Firebase
        mAuth.addAuthStateListener(mAuthListener);
        //
    }

    @Override
    public void onStop() {
        super.onStop();
        //esta parte es también de la gestión de usuarios Firebase
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        //
    }

    public void creaUsuario(final String emailTexto, final String passwordTexto){

    /*
https://firebase.google.com/docs/auth/android/password-auth

Autenticar con Firebase mediante el uso de cuentas basadas en contraseñas en Android
Puedes usar Firebase Authentication para permitir que tus usuarios autentiquen con Firebase usando sus direcciones de correo electrónico y sus contraseñas y para manejar las cuentas basadas en contraseñas de tu app.
Antes de comenzar

Cómo agregar Firebase a tu proyecto Android.
     Agrega las dependencias para Firebase Authentication tu archivo de nivel de la app build.gradle:
compile 'com.google.firebase:firebase-auth:9.6.1'
Si aún no has conectado tu app con tu proyecto Firebase, hazlo desde el Firebase console.
Habilitar el inicio de sesión con correo electrónico y contraseña:
En Firebase console, abre la sección de Autenticación.
En la pestaña Método de inicio de sesión, habilita el método de inicio de sesión con correo electrónico y contraseña y haz clic en Guardar.
Crear una cuenta basada en contraseña

Para crear una nueva cuenta de usuario con una contraseña, completa los siguientes pasos en la actividad de inicio de sesión de tu app:
*/


   /*(3)
Cuando un usuario nuevo inicia sesión usando el formulario de inicio de sesión de tu app, completa los nuevos pasos de validación de cuenta que tu app requiere,
como la verificación de que la contraseña de la cuenta nueva se escribió correctamente y cumple con tus requisitos de complejidad.


    (3) FIN*/
        // ...

        if (emailTexto != null && passwordTexto!= null) {
            Toast.makeText(getApplication(), "creando usuario", Toast.LENGTH_SHORT).show();
            mAuth.createUserWithEmailAndPassword(emailTexto, passwordTexto)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("TAG", "createUserWithEmail:onComplete:" + task.isSuccessful());
                            Toast.makeText(getApplication(), "¡hecho! " + emailTexto.toString() + passwordTexto.toString(), Toast.LENGTH_SHORT).show();

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.d("TAG", "fallo");
                            }

                            // ...
                        }
                    });
        }




//(4)
/*
Crea una cuenta nueva al pasar la dirección de correo electrónico y la contraseña del usuario nuevo a createUserWithEmailAndPassword:
 */

        /*
        Si se creó una cuenta nueva, se inicia la sesión del usuario y AuthStateListener ejecuta el callbackonAuthStateChanged callback.
        En el callback, puedes usar el método getCurrentUser para obtener los datos de cuenta del usuario
         */

// (4) FIN


    }

    public void autenticaUsuario (final String emailTexto, final String passwordTexto) {

        mAuth.signInWithEmailAndPassword(emailTexto, passwordTexto)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());
                        Toast.makeText(MainActivity.this, " autenticado "+emailTexto.toString()+passwordTexto.toString(),
                                Toast.LENGTH_SHORT).show();

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d("TAG", "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, "fallo al autenticar",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });


    }
    public void obtenPerfilUsuario (String name, String email, Uri photoUrl) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            //name = user.getDisplayName();
            email = user.getEmail();
            //photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
            if (user != null && email != null) {
                Toast.makeText(getApplication(), "obtenido perfil " + name.toString() + email.toString(), Toast.LENGTH_SHORT).show();
            }


        }
    }

}

