package com.example.potatoleaf;



import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.potatoleaf.CrystalReport.User;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class crystal_report extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 100;

    Button btnGeneratePDF;
    TextView textViewStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crystal_report);

        btnGeneratePDF = findViewById(R.id.btnGeneratePDF);
        textViewStatus = findViewById(R.id.textViewStatus);

        // Check storage permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        } else {
            btnGeneratePDF.setOnClickListener(v -> fetchDataAndGeneratePDF());
        }
    }

    private void fetchDataAndGeneratePDF() {
        String apiUrl = "https://deshytechbd.com/VDB/user/find_user_details.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, apiUrl, null,
                response -> {
                    try {
                        String status = response.getString("status");
                        if ("Success".equals(status)) {
                            JSONArray dataArray = response.getJSONArray("data");
                            List<User> users = new ArrayList<>();

                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject item = dataArray.getJSONObject(i);
                                String name = item.getString("name");
                                String email = item.getString("email");
                                String bloodGroup = item.getString("bloodGroup");  // Use correct key
                                String dateOfBirth = item.getString("dateOfBirth");  // Use correct key

                                // Add user data to the list
                                users.add(new User(name, email, bloodGroup, dateOfBirth));
                            }

                            // Generate PDF with user data
                            generatePDF(users);
                            textViewStatus.setText("PDF generated successfully!");

                        } else {
                            Toast.makeText(crystal_report.this, "Error fetching data!", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(crystal_report.this, "JSON parsing error!", Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(crystal_report.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }


    // Generate PDF using iText
    private void generatePDF(List<User> users) {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File pdfFile = new File(pdfPath, "UserDetailsReport.pdf");

        try {
            PdfWriter writer = new PdfWriter(pdfFile);
            com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("User Report"));
            document.add(new Paragraph("Generated Report with User Data"));

            // Add user data to PDF
            for (User user : users) {
                document.add(new Paragraph("Name: " + user.getName()));
                document.add(new Paragraph("Email: " + user.getEmail()));
                document.add(new Paragraph("Blood Group: " + user.getBloodGroup()));
                document.add(new Paragraph("Date of Birth: " + user.getDateOfBirth()));
                document.add(new Paragraph("------------------------------"));
            }

            document.close();
            Toast.makeText(this, "PDF generated: " + pdfFile.getAbsolutePath(), Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error generating PDF", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchDataAndGeneratePDF();
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
 }
}
}