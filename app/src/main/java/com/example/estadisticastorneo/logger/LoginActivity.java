package com.example.estadisticastorneo.logger;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.estadisticastorneo.R;
import com.example.estadisticastorneo.view.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth _firebaseauth;
    private DatabaseReference _databasereference;
    private TextView registrarse;
    private EditText _user;
    private EditText _password;
    private View _buttonLogin;

    //Necesario para inciar
    //Iniciar sesión con Google
    private ImageView btn_iniciar1;
    private SignInButton btn_iniciar;
    private GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    private static final int RC_SIGN_IN = 508;
    private static final String TAG = "Error:";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registrarse = findViewById(R.id.no_tengo_un2);
        _user = findViewById(R.id.ediTxtCorreo);
        _password =findViewById(R.id.ediTxtPass);
        _buttonLogin = findViewById(R.id.log_in);
        _firebaseauth = FirebaseAuth.getInstance();
        _databasereference = FirebaseDatabase.getInstance().getReference();
        btn_iniciar1 = findViewById(R.id.signGoogle);


    }
    public void toMain(View view){
        //Al ser el primer ingreso cargamos los datos que tengamos en las cajas de texto
        String user = _user.getText().toString();
        String pass = _password.getText().toString();
        if (!user.isEmpty() && !pass.isEmpty()) {
            if (validarEmail(user)){
                _firebaseauth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (_firebaseauth.getCurrentUser().isEmailVerified()) {
                                updateUI(task.getResult().getUser());
                            } else {
                                _firebaseauth.signOut();
                                Toast.makeText(LoginActivity.this, "Parece que no has verificado tu correo, verifica tu correo por favor", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else {
                Toast.makeText(this, "Formato de email invalido", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Ingresa usuario y/o contraseña", Toast.LENGTH_SHORT).show();
        }
    }
    public void signIn(View view) {
        //Obtenemos el dialogo de las cuentas disponibles en el dispositivo
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("338406486033-6bjflir5q6mlss1edqvtjddlibsd1dv4.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public void updateUI(FirebaseUser user){
        if(user!= null){
            Toast.makeText(LoginActivity.this,""+"Inicio exitoso",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        _firebaseauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = _firebaseauth.getCurrentUser();
                            consultaDatosUser(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Ha ocurrido un error inesperado", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void showHidePassword(View view){
        EditText password = (EditText) findViewById(R.id.ediTxtPass);
        if(view.getId()==R.id.eye){
            if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.ic_ojo_cerrado);
                //Show Password
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.ic_icono_de_ojo_abierto);
                //Hide Password
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
    public void registrarse(View view){
        Intent intent = new Intent(LoginActivity.this,SigninActivity.class);
        startActivity(intent);
    }
    public void toRecoveryPass(View view){
        Intent intent = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
        startActivity(intent);
    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    public void consultaDatosUser(FirebaseUser user){
        _databasereference.child("Users").child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.getResult().child("nombre").getValue()!= null){
                    updateUI(user);
                }else{
                    Intent intent = new Intent(LoginActivity.this, DataCompleteActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}
