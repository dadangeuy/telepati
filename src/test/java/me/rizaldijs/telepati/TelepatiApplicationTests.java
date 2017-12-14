package me.rizaldijs.telepati;

import me.rizaldijs.telepati.common.TFQuiz;
import me.rizaldijs.telepati.server.TFQuizRepository;
import me.rizaldijs.telepati.server.TelepatiApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TelepatiApplication.class)
public class TelepatiApplicationTests {

    @Autowired
    TFQuizRepository quizRepository;

	@Test
	public void contextLoads() {
	}

	@Test
    public void populateQuestion() {
        List<TFQuiz> quizzes = new LinkedList<>();
        quizzes.add(new TFQuiz("0", "Zebra adalah kuda hitam berloreng putih", false));
        quizzes.add(new TFQuiz("1", "Gajah bisa melompat", false));
        quizzes.add(new TFQuiz("2", "Seseorang dapat tertular HIV/AIDS dari nyamuk", false));
        quizzes.add(new TFQuiz("3", "Otak burung onta lebih kecil daripada bola matanya", true));
        quizzes.add(new TFQuiz("4", "Terdapat mamalia yang gak punya rambut", true));
        quizzes.add(new TFQuiz("5", "Menaburkan garam disekeliling rumah dapat mengusir ular", false));
        quizzes.add(new TFQuiz("6", "Warna biru laut adalah hasil pantulan dari biru langit", false));
        quizzes.add(new TFQuiz("7", "Tembok Besar Cina bisa dilihat dari luar angkasa", false));
        quizzes.add(new TFQuiz("8", "Semua nama planet (selain bumi) berasal dari nama dewa", true));
        quizzes.add(new TFQuiz("9", "Kelalawar itu buta", false));
        quizzes.add(new TFQuiz("10", "Cacingan merupakan penyakit menular", true));
        quizzes.add(new TFQuiz("11", "Mengonsumsi obat maag terlalu lama dapat menyebabkan infeksi lambung", true));
        quizzes.add(new TFQuiz("12", "Lambung bisa bolong karena maag", true));
        quizRepository.save(quizzes);
    }
}
