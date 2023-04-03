package com.example.estadisticastorneo.logger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.estadisticastorneo.R;
import com.example.estadisticastorneo.bean.User;
import com.example.estadisticastorneo.props.MiAdaptador;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.regex.Pattern;

public class SigninActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference _databasereference;
    FirebaseUser userA;
    EditText _nombre;
    EditText _user;
    EditText _password;
    EditText _passwordconf;
    CheckBox checkBox;
    //Varibale
    View btn_registrarte;
    User user;
    //Para iniciar sesión con Google
    private ImageView btn_iniciar1;
    private SignInButton btn_iniciar;
    private GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    private static final int RC_SIGN_IN = 508;
    private static final String TAG = "Error:";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mAuth = FirebaseAuth.getInstance();
        _databasereference = FirebaseDatabase.getInstance().getReference();
        _nombre = findViewById(R.id.ediTxtNombre);
        _user = findViewById(R.id.ediTxtCorreoReg);
        _password= findViewById(R.id.ediTxtPassRes);
        _passwordconf= findViewById(R.id.ediTxtPassResConf);
        checkBox = findViewById(R.id.checkBoxTerminos);
        btn_registrarte = findViewById(R.id.btn_registrarse);
        btn_registrarte.setClickable(false);
        btn_registrarte.setEnabled(false);
        btn_registrarte.setBackgroundResource(R.drawable.btndesha);
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
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            consultaDatosUser(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SigninActivity.this, "Ha ocurrido un error inesperado", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    public void updateUI(FirebaseUser user){
        if(user!= null){
            Intent intent = new Intent(SigninActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void agregaUsuario(View view){
        String nombre=_nombre.getText().toString();
        String email=_user.getText().toString();
        String password= _password.getText().toString();
        String passwordConf = _passwordconf.getText().toString();
        if(!email.isEmpty() && !password.isEmpty()  && !passwordConf.isEmpty() && !nombre.isEmpty()) {
            if (validarEmail(email)) {
                if (password.length() >= 6) {
                    if (passwordConf.compareTo(password) == 0) {
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String userID = mAuth.getCurrentUser().getUid();
                                    user = new User();
                                    user.setNombre(nombre);
                                    user.setEmail(email);
                                    user.setPassword(password);
                                    user.setActive(false);
                                    addToDataBase(userID);
                                    userA = mAuth.getCurrentUser();
                                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(SigninActivity.this, "Email de verificacion enviado", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(SigninActivity.this, EmailVerficationActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(SigninActivity.this, "Mensaje de verificacion no enviado, ha ocurrido un error", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

//
                                } else {
                                    Toast.makeText(SigninActivity.this, "El correo ya se encuentra registrado anteriormente o hay un formato de correo desconocido", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "La contraseña debe ser mayor o igual a 6 digitos", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Formato de Email invalido", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show();
        }
    }



    private void addToDataBase(String userID){
        if(user!= null){
            _databasereference.child("Users").child(userID).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Log.e("Usuario","Se agrego correctamente");
                    }else{
                        Toast.makeText(SigninActivity.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void habilitaBoton(View view){
        if (checkBox.isChecked()){
            checkBox.setButtonDrawable(R.drawable.ic_bola_rellena_de_aceptar_terminos);
            btn_registrarte.setClickable(true);
            btn_registrarte.setEnabled(true);
            btn_registrarte.setBackgroundResource(R.drawable.btndesha);
        }else{
            checkBox.setButtonDrawable(R.drawable.ic_aceptar_terminos);
            btn_registrarte.setClickable(false);
            btn_registrarte.setEnabled(false);
            btn_registrarte.setBackgroundResource(R.drawable.ic_boton_de_siguiente1);
            Toast.makeText(this, "Porfavor acepta Términos y condiciones", Toast.LENGTH_SHORT).show();
        }

    }
    public void toLogin(View view){
        Intent intent = new Intent(SigninActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void toSignin(View view){
        Intent intent = new Intent(SigninActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void showHidePassword(View view){
        EditText password = (EditText) findViewById(R.id.ediTxtPassRes);
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
        EditText password2 = (EditText) findViewById(R.id.ediTxtPassResConf);
        if(view.getId()==R.id.eyeconf){
            if(password2.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.ic_ojo_cerrado);
                //Show Password
                password2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.ic_icono_de_ojo_abierto);
                //Hide Password
                password2.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    public void consultaDatosUser(FirebaseUser _user){
        _databasereference.child("Users").child(_user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.getResult().child("nombre").getValue()!= null){
                    updateUI(_user);
                }else{
                    Intent intent = new Intent(SigninActivity.this, DataCompleteActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}
