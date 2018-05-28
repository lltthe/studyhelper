package com.example.lamlethanhthe.studyhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView txt = (TextView)findViewById(R.id.txtAbout);

        txt.setText("Authors - Tác giả:\n"
                + "     Lâm Lê Thanh Thế                  1551034\n"
                + "     Nguyễn Trần Phước Thịnh     1551038\n\n"
                + " This application serves the final project for the course CS426 of class 15CTT, APCS, HCMUS. "
                + "The interface is built along with English and Vietnamese, based on the user's system language settings. "
                + "Source codes will be provided together with the apk file, as well as associated documents. "
                + "The user can read carefully before using, or just click, long-click, swipe, drag, and smash everything shown on the screen.\n"
                + " *Not all ideas and codes are from the authors, please check the references and give credits on reusing.\n\n\n"

                + " Ứng dụng này là đồ án cuối kì cho môn CS426 của lớp 15CTT, CTTT, ĐH KHTN - ĐH QG TPHCM. "
                + "Có 2 ngôn ngữ tiếng Anh và tiếng Việt, được kích hoạt tự động dựa theo ngôn ngữ của hệ thống điện thoại người dùng. "
                + "Mã nguồn, file apk và các tài liệu liên quan đều được đóng gói chung. Người dùng có thể đọc kĩ hoặc tự do khám phá tuỳ thích.\n"
                + " *Không phải tất cả các ý tưởng và mã nguồn đều là của riêng nhóm tác giả, vui lòng kiểm tra kĩ và trích dẫn các nguồn tham khảo đi kèm khi cần thiết.\n\n" +
                "Dued on September, 2017 - Hoàn tất vào tháng 9, 2017");
    }
}
