package com.example.estadisticastorneo.logger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.estadisticastorneo.R;
import com.example.estadisticastorneo.statics.Dialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class ForgetPasswordActivity extends AppCompatActivity {
    private FirebaseAuth _firebaseauth;
    private EditText _user;
    Dialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        _firebaseauth = FirebaseAuth.getInstance();
        _user =findViewById(R.id.ediTxtCorreoForget);
        mDialog = new Dialog(this);

    }
    public void recoveryPassword(View view){
        mDialog.show();
        String email = _user.getText().toString();
        if (!email.isEmpty()) {
            _firebaseauth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ForgetPasswordActivity.this, "Link de recuperación enviado...", Toast.LENGTH_LONG).show();
                        Bundle email_ = new Bundle();
                        email_.putString("email",email);
                        mDialog.dissmis();
                        Intent intent = new Intent(ForgetPasswordActivity.this, PasswordRestarActivity.class);
                        intent.putExtras(email_);
                        startActivity(intent);
                    }else{
                        mDialog.dissmis();
                        Toast.makeText(ForgetPasswordActivity.this, "Ha ocurrido un error...Contacta a soporte tecnico" + email, Toast.LENGTH_LONG).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    mDialog.dissmis();
                    Toast.makeText(ForgetPasswordActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }else{
            mDialog.dissmis();
            Toast.makeText(this, "¡¡Ingresa un correo por favor!!", Toast.LENGTH_SHORT).show();
        }
    }
    public void onBack(View view){
        super.onBackPressed();
    }
}
