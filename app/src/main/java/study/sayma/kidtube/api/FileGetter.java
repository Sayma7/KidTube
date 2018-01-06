package study.sayma.kidtube.api;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import study.sayma.kidtube.activity.YoutubePLayListerActivity;
import study.sayma.kidtube.interfaces.ApiCallback;
import study.sayma.kidtube.utils.P;

/**
 * Created by Sayma on 12/26/2017.
 */

public class FileGetter extends AsyncTask<Void, Void, String> {

    private static final String TAG = FileGetter.class.getSimpleName();

    private ApiCallback apiCallback;
    private boolean isOk = false;

    public FileGetter(ApiCallback apiCallback) {
        this.apiCallback = apiCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        apiCallback.onStart();
        isOk = false;
    }

    @Override
    protected String doInBackground(Void... voids) {
        isOk = true;
        return "{\n" +
                "  \"playlist\" : [\n" +
                "      {\n" +
                "        \"id\" : \"PLnG4P8mEMi0HMfnV3knx1UPrjcsk5_bqO\",\n" +
                "        \"name\" : \"Songs\",\n" +
                "        \"thumb\" : \"https://i.ytimg.com/vi/tbbKjDjMDok/mqdefault.jpg\",\n" +
                "        \"videos\" : [\n" +
                "          {\n" +
                "            \"id\" : \"tbbKjDjMDok\",\n" +
                "            \"name\" : \"Little Snowflake\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/tbbKjDjMDok/mqdefault.jpg\",\n" +
                "            \"length\" : 168\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\" : \"mGAYzlqj-aE\",\n" +
                "            \"name\" : \"S-A-N-T-A\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/mGAYzlqj-aE/mqdefault.jpg\",\n" +
                "            \"length\" : 169\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\" : \"eQ34DSTjsLQ\",\n" +
                "            \"name\" : \"Jingle Bells\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/eQ34DSTjsLQ/mqdefault.jpg\",\n" +
                "            \"length\" : 189\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\" : \"hNkvV4PR-q0\",\n" +
                "            \"name\" : \"We Wish You A Merry Christmas\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/hNkvV4PR-q0/mqdefault.jpg\",\n" +
                "            \"length\" : 120\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\" : \"lW5dIQ_F4tQ\",\n" +
                "            \"name\" : \"Christmas Special with Alisa from FROZEN Land\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/lW5dIQ_F4tQ/mqdefault.jpg\",\n" +
                "            \"length\" : 212\n" +
                "          },{\n" +
                "            \"id\" : \"tTSX3bLsMdY\",\n" +
                "            \"name\" : \"Happy birthday song Chipmunks\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/tTSX3bLsMdY/mqdefault.jpg\",\n" +
                "            \"length\" : 83\n" +
                "          },{\n" +
                "            \"id\" : \"bKTbdej8T-Q\",\n" +
                "            \"name\" : \"CUPPY CAKE\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/bKTbdej8T-Q/mqdefault.jpg\",\n" +
                "            \"length\" : 56\n" +
                "          },{\n" +
                "            \"id\" : \"l4WNrvVjiTw\",\n" +
                "            \"name\" : \"If You are Happy\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/l4WNrvVjiTw/mqdefault.jpg\",\n" +
                "            \"length\" : 123\n" +
                "          },{\n" +
                "            \"id\" : \"-jBfb33_KHU\",\n" +
                "            \"name\" : \"Put On Your Shoes\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/-jBfb33_KHU/mqdefault.jpg\",\n" +
                "            \"length\" : 181\n" +
                "          },{\n" +
                "            \"id\" : \"4XLQpRI_wOQ\",\n" +
                "            \"name\" : \"This Is The Way\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/4XLQpRI_wOQ/mqdefault.jpg\",\n" +
                "            \"length\" : 138\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\" : \"PLnG4P8mEMi0FgXpRNi4TQ3W6Dd-fu8sXU\",\n" +
                "        \"name\" : \"Rhymes\",\n" +
                "        \"thumb\" : \"https://i.ytimg.com/vi/0n_J2z-ILXo/mqdefault.jpg\",\n" +
                "        \"videos\" : [\n" +
                "          {\n" +
                "              \"id\" : \"6-PULmyf_Pg\",\n" +
                "            \"name\" : \"Kindergarten Nursery Rhymes\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/6-PULmyf_Pg/mqdefault.jpg\",\n" +
                "            \"length\" : 3652\n" +
                "          },\n" +
                "           {\n" +
                "              \"id\" : \"IfNdO5OrHJc\",\n" +
                "            \"name\" : \"Three Little Kittens\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/IfNdO5OrHJc/mqdefault.jpg\",\n" +
                "            \"length\" : 253\n" +
                "          },\n" +
                "           {\n" +
                "              \"id\" : \"0n_J2z-ILXo\",\n" +
                "            \"name\" : \"Humpty Dumpty\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/0n_J2z-ILXo/mqdefault.jpg\",\n" +
                "            \"length\" : 1854\n" +
                "          },\n" +
                "           {\n" +
                "              \"id\" : \"PHofsaaie0A\",\n" +
                "            \"name\" : \"One Little Finger\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/PHofsaaie0A/mqdefault.jpg\",\n" +
                "            \"length\" : 3777\n" +
                "          },\n" +
                "           {\n" +
                "              \"id\" : \"DsGvkftHHzE\",\n" +
                "            \"name\" : \"Five Little Ducks Went Out One Day\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/DsGvkftHHzE/mqdefault.jpg\",\n" +
                "            \"length\" : 1109\n" +
                "          },\n" +
                "           {\n" +
                "              \"id\" : \"DgJP2kvx9FY\",\n" +
                "            \"name\" : \"Twinkle, Twinkle, Little Star\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/DgJP2kvx9FY/mqdefault.jpg\",\n" +
                "            \"length\" : 3541\n" +
                "          },\n" +
                "           {\n" +
                "              \"id\" : \"H0SbnlDsdAc\",\n" +
                "            \"name\" : \"Kids English Nursery Rhymes\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/H0SbnlDsdAc/mqdefault.jpg\",\n" +
                "            \"length\" : 3993\n" +
                "          },\n" +
                "           {\n" +
                "              \"id\" : \"rgVnym9fSK4\",\n" +
                "            \"name\" : \"Three Little Kittens Nursery Rhymes for Babies\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/rgVnym9fSK4/mqdefault.jpg\",\n" +
                "            \"length\" : 3171\n" +
                "          },\n" +
                "           {\n" +
                "              \"id\" : \"JTIXGdsM73M\",\n" +
                "            \"name\" : \"Learn Colors, Numbers and ABCs. ABC Songs for Kids. Alphabet Song. Nursery Rhymes\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/JTIXGdsM73M/mqdefault.jpg\",\n" +
                "            \"length\" : 4375\n" +
                "          },\n" +
                "           {\n" +
                "              \"id\" : \"vQ7yj1yv7_4\",\n" +
                "            \"name\" : \"Baby Kitten Care | Kids Songs collection & Nursery Rhymes\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/vQ7yj1yv7_4/mqdefault.jpg\",\n" +
                "            \"length\" : 1203\n" +
                "          },\n" +
                "           {\n" +
                "              \"id\" : \"u-mT4GGtgBc\",\n" +
                "            \"name\" : \"Johny Johny Yes Papa\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/u-mT4GGtgBc/mqdefault.jpg\",\n" +
                "            \"length\" : 154\n" +
                "          },\n" +
                "           {\n" +
                "              \"id\" : \"rYz_nYMqGOY\",\n" +
                "            \"name\" : \"Rain Rain Go Away ( Come Again )\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/rYz_nYMqGOY/mqdefault.jpg\",\n" +
                "            \"length\" : 148\n" +
                "          },\n" +
                "           {\n" +
                "              \"id\" : \"LFrKYjrIDs8\",\n" +
                "            \"name\" : \"Rain Rain Go Away\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/LFrKYjrIDs8/mqdefault.jpg\",\n" +
                "            \"length\" : 155\n" +
                "          }\n" +
                "        ]\n" +
                "     },\n" +
                "    {\n" +
                "        \"id\" : \"PLnG4P8mEMi0FgXpRNi4TQ3W6Dd-fu8sXU\",\n" +
                "        \"name\" : \"Alphabets\",\n" +
                "        \"thumb\" : \"https://i.ytimg.com/vi/seZN7oCRYa0/mqdefault.jpg\",\n" +
                "        \"videos\" : [\n" +
                "          {\n" +
                "              \"id\" : \"hq3yfQnllfQ\",\n" +
                "            \"name\" : \"Phonics Song with TWO Words - A For Apple\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/hq3yfQnllfQ/mqdefault.jpg\",\n" +
                "            \"length\" : 245\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"kV189Or1s2M\",\n" +
                "            \"name\" : \"learning alphabets for kids | Phonetics for kids\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/kV189Or1s2M/mqdefault.jpg\",\n" +
                "            \"length\" : 1423\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"_UR-l3QI2nE\",\n" +
                "            \"name\" : \"ABC SONG | ABC Songs for Children - 13 Alphabet Songs\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/_UR-l3QI2nE/mqdefault.jpg\",\n" +
                "            \"length\" : 3051\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"yGq8_qDeviY\",\n" +
                "            \"name\" : \"Letters For Toddlers | Alphabets For Kids\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/yGq8_qDeviY/mqdefault.jpg\",\n" +
                "            \"length\" : 543\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"e0-UMSk9_R8\",\n" +
                "            \"name\" : \"Play and Learn ALPHABETS with Play Doh for Children\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/e0-UMSk9_R8/mqdefault.jpg\",\n" +
                "            \"length\" : 1330\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"JTIXGdsM73M\",\n" +
                "            \"name\" : \"Learn Colors, Numbers and ABCs. ABC Songs for Kids. Alphabet Song.\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/JTIXGdsM73M/mqdefault.jpg\",\n" +
                "            \"length\" : 4415\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"seZN7oCRYa0\",\n" +
                "            \"name\" : \"Alphabet for Kids to Learn\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/seZN7oCRYa0/mqdefault.jpg\",\n" +
                "            \"length\" : 1870\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"HaQZw9Sxlu8\",\n" +
                "            \"name\" : \"Best ABC Alphabet Song\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/HaQZw9Sxlu8/mqdefault.jpg\",\n" +
                "            \"length\" : 185\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"66c-DS_0Bdw\",\n" +
                "            \"name\" : \"ABCD Song | ABC Song for children | ABCD Alphabet Song\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/66c-DS_0Bdw/mqdefault.jpg\",\n" +
                "            \"length\" : 71\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"hxvYjqPnROU\",\n" +
                "            \"name\" : \"Alphabet songs | Phonics Songs | ABC Song for children - 3D Animation\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/hxvYjqPnROU/mqdefault.jpg\",\n" +
                "            \"length\" : 129\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"8VyfmtOlgRg\",\n" +
                "            \"name\" : \"Learn the Alphabet! Learn the ABCs with our funny people characters\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/8VyfmtOlgRg/mqdefault.jpg\",\n" +
                "            \"length\" : 127\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"trFAVIUt7HU\",\n" +
                "            \"name\" : \"Frozen Land ABC Song: Let Us Learn with Alisa!\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/trFAVIUt7HU/mqdefault.jpg\",\n" +
                "            \"length\" : 133\n" +
                "          }\n" +
                "       ]\n" +
                "     },\n" +
                "    {\n" +
                "        \"id\" : \"PLnG4P8mEMi0HUpg-jMlw25B0bp5qDb6lu\",\n" +
                "        \"name\" : \"Stories\",\n" +
                "        \"thumb\" : \"https://i.ytimg.com/vi/BeWWyjZqRyE/mqdefault.jpg\",\n" +
                "        \"videos\" : [\n" +
                "          {\n" +
                "              \"id\" : \"BeWWyjZqRyE\",\n" +
                "            \"name\" : \"KIDS STORIES - STORIES FOR KIDS || ANIMATED ENGLISH STORIES\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/BeWWyjZqRyE/mqdefault.jpg\",\n" +
                "            \"length\" : 7501\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"3qKTKkBLpgo\",\n" +
                "            \"name\" : \"The Lunch Thief | Plus Many More Bedtime Stories For Kids in English\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/3qKTKkBLpgo/mqdefault.jpg\",\n" +
                "            \"length\" : 2149\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"XbgjH0ApKoc\",\n" +
                "            \"name\" : \"Rapunzel story & Rapunzel Songs\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/XbgjH0ApKoc/mqdefault.jpg\",\n" +
                "            \"length\" : 1452\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"2EwKdFdcXm8\",\n" +
                "            \"name\" : \"Short Stories For Kids-English Story Collection\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/2EwKdFdcXm8/mqdefault.jpg\",\n" +
                "            \"length\" : 4796\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"h2Xj-A7HsYE\",\n" +
                "            \"name\" : \"The Elephant And The Ant\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/h2Xj-A7HsYE/mqdefault.jpg\",\n" +
                "            \"length\" : 386\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"W1iYtHqEPAc\",\n" +
                "            \"name\" : \"THE TALE OF THREE FISH | THE FISH STORY\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/W1iYtHqEPAc/mqdefault.jpg\",\n" +
                "            \"length\" : 410\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"wZq2tyLNPRU\",\n" +
                "            \"name\" : \"19 Best Short English Stories for Kids\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/wZq2tyLNPRU/mqdefault.jpg\",\n" +
                "            \"length\" : 2260\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"52g8NiopV0U\",\n" +
                "            \"name\" : \"THE SELFISH GIANT - KIDS HUT STORIES\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/52g8NiopV0U/mqdefault.jpg\",\n" +
                "            \"length\" : 747\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"Ik-ND5zTeYc\",\n" +
                "            \"name\" : \"THE NUTCRACKER - KIDS HUT STORIES\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/Ik-ND5zTeYc/mqdefault.jpg\",\n" +
                "            \"length\" : 593\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"3F0WLIY0dLg\",\n" +
                "            \"name\" : \"The Honest Girl\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/3F0WLIY0dLg/mqdefault.jpg\",\n" +
                "            \"length\" : 394\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"BjrKiRNr8cw\",\n" +
                "            \"name\" : \"Learning To Obey | Moral Stories in English\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/BjrKiRNr8cw/mqdefault.jpg\",\n" +
                "            \"length\" : 255\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"0Dvmryh2Djg\",\n" +
                "            \"name\" : \"The Crow And Old Woman | Classic Short Stories\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/0Dvmryh2Djg/mqdefault.jpg\",\n" +
                "            \"length\" : 301\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"wWSxikeQKpY\",\n" +
                "            \"name\" : \"The Goose and The Golden Egg - Aesop fables\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/wWSxikeQKpY/mqdefault.jpg\",\n" +
                "            \"length\" : 147\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "    {\n" +
                "        \"id\" : \"PLnG4P8mEMi0HUpg-jMlw25B0bp5qDb6lu\",\n" +
                "        \"name\" : \"Jokes\",\n" +
                "        \"thumb\" : \"https://i.ytimg.com/vi/Ej-H_Axox_g/mqdefault.jpg\",\n" +
                "        \"videos\" : [\n" +
                "          {\n" +
                "              \"id\" : \"Q0BRWnjlX9c\",\n" +
                "            \"name\" : \"20 Jokes For Kids\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/Q0BRWnjlX9c/mqdefault.jpg\",\n" +
                "            \"length\" : 378\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"cK1LdvpglsE\",\n" +
                "            \"name\" : \"Funny Jokes for Children\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/cK1LdvpglsE/mqdefault.jpg\",\n" +
                "            \"length\" : 120\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"ZcY9r5Mz6-M\",\n" +
                "            \"name\" : \"Disney Jokes for Kids\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/ZcY9r5Mz6-M/mqdefault.jpg\",\n" +
                "            \"length\" : 153\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"vl_ZjLbC-Aw\",\n" +
                "            \"name\" : \"Funny Classroom Jokes\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/vl_ZjLbC-Aw/mqdefault.jpg\",\n" +
                "            \"length\" : 1916\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"YVK7JHyBcvk\",\n" +
                "            \"name\" : \"Disney Jokes to Brighten Your Day!\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/YVK7JHyBcvk/mqdefault.jpg\",\n" +
                "            \"length\" : 218\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"62aA6vtwUdA\",\n" +
                "            \"name\" : \"Kids Jokes | Funny Jokes\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/62aA6vtwUdA/mqdefault.jpg\",\n" +
                "            \"length\" : 156\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"3F_pUiRdQ8Q\",\n" +
                "            \"name\" : \"Knock Knock: Funny Kids tell the Best Jokes\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/3F_pUiRdQ8Q/mqdefault.jpg\",\n" +
                "            \"length\" : 186\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"Ej-H_Axox_g\",\n" +
                "            \"name\" : \"Silly Jokes for Kids [with Music]\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/Ej-H_Axox_g/mqdefault.jpg\",\n" +
                "            \"length\" : 148\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"qjgwE_SQJ0Q\",\n" +
                "            \"name\" : \"Funny Classroom Jokes - Animated Collection\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/qjgwE_SQJ0Q/mqdefault.jpg\",\n" +
                "            \"length\" : 174\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"HkwZJg_iubk\",\n" +
                "            \"name\" : \"Funny Classroom Joke - Exam Paper\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/HkwZJg_iubk/mqdefault.jpg\",\n" +
                "            \"length\" : 61\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"f5hknvYcJCg\",\n" +
                "            \"name\" : \"Funny Classroom Joke - 28 Days\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/f5hknvYcJCg/mqdefault.jpg\",\n" +
                "            \"length\" : 65\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"SyDE1Qc0EOQ\",\n" +
                "            \"name\" : \"Funny Classroom Joke – I Got A Hundred\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/SyDE1Qc0EOQ/mqdefault.jpg\",\n" +
                "            \"length\" : 60\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"lflWTe5Q3dE\",\n" +
                "            \"name\" : \"Funny Classroom Joke – Find X?\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/lflWTe5Q3dE/mqdefault.jpg\",\n" +
                "            \"length\" : 61\n" +
                "          }\n" +
                "        ]\n" +
                "     },\n" +
                "    {\n" +
                "        \"id\" : \"PLnG4P8mEMi0HrfdHmbKpZQFDY4LsaFcZS\",\n" +
                "        \"name\" : \"Funny\",\n" +
                "        \"thumb\" : \"https://i.ytimg.com/vi/dmVzCf_G1-k/mqdefault.jpg\",\n" +
                "        \"videos\" : [\n" +
                "          {\n" +
                "              \"id\" : \"QaXqh51LerQ\",\n" +
                "            \"name\" : \"TooToo Boy - Meditation Episode\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/QaXqh51LerQ/mqdefault.jpg\",\n" +
                "            \"length\" : 758\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"dmVzCf_G1-k\",\n" +
                "            \"name\" : \"Funny Kids Laughing Hysterically Compilation\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/dmVzCf_G1-k/mqdefault.jpg\",\n" +
                "            \"length\" : 1046\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"rYPyixyXwkQ\",\n" +
                "            \"name\" : \"Baby Dance Competition Funny Video\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/rYPyixyXwkQ/mqdefault.jpg\",\n" +
                "            \"length\" : 92\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"o-ajSt2FcT0\",\n" +
                "            \"name\" : \"Kids Funny Video \",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/o-ajSt2FcT0/mqdefault.jpg\",\n" +
                "            \"length\" : 430\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"ZuMQlAYcB2g\",\n" +
                "            \"name\" : \"Kids Funny Video ★ Funny Videos Of Kids\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/ZuMQlAYcB2g/mqdefault.jpg\",\n" +
                "            \"length\" : 263\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"pWTtEn9pio4\",\n" +
                "            \"name\" : \"Funny videos : Vodafone ZooZoo Ads\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/pWTtEn9pio4/mqdefault.jpg\",\n" +
                "            \"length\" : 301\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"aI0eJIn_GNo\",\n" +
                "            \"name\" : \"Vodafone Zoozoos\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/aI0eJIn_GNo/mqdefault.jpg\",\n" +
                "            \"length\" : 160\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"8-p0CxW87pg\",\n" +
                "            \"name\" : \"Baby&Me, New Funny Evian Advert\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/8-p0CxW87pg/mqdefault.jpg\",\n" +
                "            \"length\" : 77\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"Uh-jzz3rX-0\",\n" +
                "            \"name\" : \"Evian water advert for rollerskating babies\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/Uh-jzz3rX-0/mqdefault.jpg\",\n" +
                "            \"length\" : 62\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"rYlHFcNtfzA\",\n" +
                "            \"name\" : \"Funny Babies Doing Exercises Compilation\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/rYlHFcNtfzA/mqdefault.jpg\",\n" +
                "            \"length\" : 321\n" +
                "          }\n" +
                "       ]\n" +
                "     },\n" +
                "    {\n" +
                "        \"id\" : \"PLnG4P8mEMi0H1DSKoqTQTcSygl3EHLxwy\",\n" +
                "        \"name\" : \"Animals\",\n" +
                "        \"thumb\" : \"https://i.ytimg.com/vi/qqEHiG74xa4/mqdefault.jpg\",\n" +
                "        \"videos\" : [\n" +
                "          {\n" +
                "              \"id\" : \"qqEHiG74xa4\",\n" +
                "            \"name\" : \"Top Best Animals for Kids and Families | Pets for Kids \",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/qqEHiG74xa4/mqdefault.jpg\",\n" +
                "            \"length\" : 610\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"a4YPullnX0Q\",\n" +
                "            \"name\" : \"Going To the Forest | Wild Animals for Kids\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/a4YPullnX0Q/mqdefault.jpg\",\n" +
                "            \"length\" : 2691\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"vQPSuPKa6bQ\",\n" +
                "            \"name\" : \"Animals for Kids | Domestic Animals for Kids\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/vQPSuPKa6bQ/mqdefault.jpg\",\n" +
                "            \"length\" : 168\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"7K0uS9LpKgU\",\n" +
                "            \"name\" : \"Learn Domestic Animals Sounds For Children | Best Way To Learn Animals Names\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/7K0uS9LpKgU/mqdefault.jpg\",\n" +
                "            \"length\" : 632\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"QhQpT9-8Bss\",\n" +
                "            \"name\" : \"Learning Wild Animals for Kids - Stacking Tsum Tsum Style\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/QhQpT9-8Bss/mqdefault.jpg\",\n" +
                "            \"length\" : 380\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"RVJbKPW3Crs\",\n" +
                "            \"name\" : \"Farm animals name and sound\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/RVJbKPW3Crs/mqdefault.jpg\",\n" +
                "            \"length\" : 189\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"O9qnZO87yA8\",\n" +
                "            \"name\" : \"Learning Wild Animals Names and Sounds for kids in English\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/O9qnZO87yA8/mqdefault.jpg\",\n" +
                "            \"length\" : 604\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"F7aHZZlI9bQ\",\n" +
                "            \"name\" : \"Funny Animals Dance Video for Children \",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/F7aHZZlI9bQ/mqdefault.jpg\",\n" +
                "            \"length\" : 189\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"Woc5c8mePR4\",\n" +
                "            \"name\" : \"Animal Sounds Songs | + More Super Simple Songs\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/Woc5c8mePR4/mqdefault.jpg\",\n" +
                "            \"length\" : 3601\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"QjuZhPjMnkE\",\n" +
                "            \"name\" : \"Funny Baby Play With Zoo Animals On The Playground Nursery Rhymes Songs\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/QjuZhPjMnkE/mqdefault.jpg\",\n" +
                "            \"length\" : 1130\n" +
                "          }\n" +
                "       ] \n" +
                "     },\n" +
                "    {\n" +
                "        \"id\" : \"PLnG4P8mEMi0Hc4qTYR0TzIkX_VAqM5zFu\",\n" +
                "        \"name\" : \"Math\",\n" +
                "        \"thumb\" : \"https://i.ytimg.com/vi/CCk0Zw-kXuI/mqdefault.jpg\",\n" +
                "        \"videos\" : [\n" +
                "          {\n" +
                "              \"id\" : \"N0_TWQTrJ-k\",\n" +
                "            \"name\" : \"Basic Math For Kids: Addition and Subtraction, Science games, Preschool and Kindergarten Activities\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/N0_TWQTrJ-k/mqdefault.jpg\",\n" +
                "            \"length\" : 1298\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"Fe8u2I3vmHU\",\n" +
                "            \"name\" : \"Basic Addition for Kids\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/Fe8u2I3vmHU/mqdefault.jpg\",\n" +
                "            \"length\" : 328\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"AQ7THUKx6Es\",\n" +
                "            \"name\" : \"Learn Maths - Addition\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/AQ7THUKx6Es/mqdefault.jpg\",\n" +
                "            \"length\" : 453\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"exsFIpQVNIw\",\n" +
                "            \"name\" : \"Counting Songs - Learning Addition\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/exsFIpQVNIw/mqdefault.jpg\",\n" +
                "            \"length\" : 192\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"kNJSKhIT4U4\",\n" +
                "            \"name\" : \"Counting Song | Addition Song for Kids\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/kNJSKhIT4U4/mqdefault.jpg\",\n" +
                "            \"length\" : 95\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"CCk0Zw-kXuI\",\n" +
                "            \"name\" : \"Addition + 1 Kids Song | Counting and Numbers\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/CCk0Zw-kXuI/mqdefault.jpg\",\n" +
                "            \"length\" : 105\n" +
                "          },\n" +
                "          {\n" +
                "             \"id\" : \"diMJIlv-4N0\",\n" +
                "            \"name\" : \"Count to 10 | Counting Song for Kids\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/diMJIlv-4N0/mqdefault.jpg\",\n" +
                "            \"length\" : 123\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"JTIXGdsM73M\",\n" +
                "            \"name\" : \"Learn Colors, Numbers and ABCs. ABC Songs for Kids. Alphabet Song\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/JTIXGdsM73M/mqdefault.jpg\",\n" +
                "            \"length\" : 4375\n" +
                "          }\n" +
                "        ]\n" +
                "     },\n" +
                "     {\n" +
                "        \"id\" : \"PLnG4P8mEMi0FBrGqlAENsdHQFrko2cRqp\",\n" +
                "        \"name\" : \"Science\",\n" +
                "        \"thumb\" : \"https://i.ytimg.com/vi/Bf8V7cF8t48/mqdefault.jpg\",\n" +
                "        \"videos\" : [\n" +
                "          {\n" +
                "              \"id\" : \"Bf8V7cF8t48\",\n" +
                "            \"name\" : \"Things To Know - Kids Video | Basic Science For Kids | Good To Know\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/Bf8V7cF8t48/mqdefault.jpg\",\n" +
                "            \"length\" : 719\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"24wO1G_7fyc\",\n" +
                "            \"name\" : \"Layers of the Earth\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/24wO1G_7fyc/mqdefault.jpg\",\n" +
                "            \"length\" : 479\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"x_sJzVe9P_8\",\n" +
                "            \"name\" : \"Greenhouse Effect for Kids\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/x_sJzVe9P_8/mqdefault.jpg\",\n" +
                "            \"length\" : 434\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"PqxMzKLYrZ4\",\n" +
                "            \"name\" : \"Global Warming\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/PqxMzKLYrZ4/mqdefault.jpg\",\n" +
                "            \"length\" : 370\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"bFczvJp0bpU\",\n" +
                "            \"name\" : \"Digestive System of Human Body\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/bFczvJp0bpU/mqdefault.jpg\",\n" +
                "            \"length\" : 228\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"sAKyhfxxr7s\",\n" +
                "            \"name\" : \"Air Pollution\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/sAKyhfxxr7s/mqdefault.jpg\",\n" +
                "            \"length\" : 297\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"4MHn9Q5NtdY\",\n" +
                "            \"name\" : \"10 Easy Science Experiments - That Will Amaze\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/4MHn9Q5NtdY/mqdefault.jpg\",\n" +
                "            \"length\" : 487\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"OqHp03RRTDs\",\n" +
                "            \"name\" : \"Learn about Pollution\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/OqHp03RRTDs/mqdefault.jpg\",\n" +
                "            \"length\" : 346\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"gEk6JLJNg0U\",\n" +
                "            \"name\" : \"What is Environment And How To Keep It Clean? | Environmental Studies\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/gEk6JLJNg0U/mqdefault.jpg\",\n" +
                "            \"length\" : 179\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"31XrAaH8YqI\",\n" +
                "            \"name\" : \"Saving Environment\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/31XrAaH8YqI/mqdefault.jpg\",\n" +
                "            \"length\" : 197\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\" : \"PLnG4P8mEMi0EVYz3ztVCqqc4lVWTyFi5Y\",\n" +
                "        \"name\" : \"Adventure\",\n" +
                "        \"thumb\" : \"https://i.ytimg.com/vi/hEzER6cfu9k/mqdefault.jpg\",\n" +
                "        \"videos\" : [\n" +
                "          {\n" +
                "              \"id\" : \"hEzER6cfu9k\",\n" +
                "            \"name\" : \"Adventure Kids 1 - Kids Nerf War - Operation Sister Rescue\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/hEzER6cfu9k/mqdefault.jpg\",\n" +
                "            \"length\" : 316\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"-JryyTDc2rU\",\n" +
                "            \"name\" : \"The Adventure Of Tom Sawyer - Bedtime Story For Kids || Moral Stories\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/-JryyTDc2rU/mqdefault.jpg\",\n" +
                "            \"length\" : 874\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"722EXLTigpg\",\n" +
                "            \"name\" : \"Adventure Kids 2 - Operation Animal Rescue\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/722EXLTigpg/mqdefault.jpg\",\n" +
                "            \"length\" : 413\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"7AsmU2pr7Hw\",\n" +
                "            \"name\" : \"Forest Adventures\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/7AsmU2pr7Hw/mqdefault.jpg\",\n" +
                "            \"length\" : 100\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"McdH8-N1UKQ\",\n" +
                "            \"name\" : \"Day at Forest Adventure Kids Course with Lucas!\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/McdH8-N1UKQ/mqdefault.jpg\",\n" +
                "            \"length\" : 226\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"0p12wt_MocA\",\n" +
                "            \"name\" : \"Forest Adventure Kids Course Upper Layer\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/0p12wt_MocA/mqdefault.jpg\",\n" +
                "            \"length\" : 192\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"zvw84Gplbbs\",\n" +
                "            \"name\" : \"Roo Pibi A Forest Adventure Fun Game\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/zvw84Gplbbs/mqdefault.jpg\",\n" +
                "            \"length\" : 763\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\" : \"PLnG4P8mEMi0Fx7KmE-HvYGhjfIurI1z6h\",\n" +
                "        \"name\" : \"Games\",\n" +
                "        \"thumb\" : \"https://i.ytimg.com/vi/U9Q6V7xquQo/mqdefault.jpg\",\n" +
                "        \"videos\" : [\n" +
                "          {\n" +
                "              \"id\" : \"U9Q6V7xquQo\",\n" +
                "            \"name\" : \"Best Games for Kids - Ava the 3D Doll iPad Gameplay HD\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/U9Q6V7xquQo/mqdefault.jpg\",\n" +
                "            \"length\" : 852\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"MKVaQ20t8cc\",\n" +
                "            \"name\" : \"Little Builders Kids Games | Trucks, Cranes & Digger\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/MKVaQ20t8cc/mqdefault.jpg\",\n" +
                "            \"length\" : 774\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"0OUKzFGXt4A\",\n" +
                "            \"name\" : \"Fun Kids Color Learning Game - Kids Learn Clean Up, Make Up Wash Dishes - Sweet Baby Girl Clean Up\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/0OUKzFGXt4A/mqdefault.jpg\",\n" +
                "            \"length\" : 1391\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"w1PCppp10lg\",\n" +
                "            \"name\" : \" Coco Star: Super Models Competition\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/w1PCppp10lg/mqdefault.jpg\",\n" +
                "            \"length\" : 1210\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"8uoZzj9vEww\",\n" +
                "            \"name\" : \"Learn Colors for Children with Baby Game Play Wooden Toy Funny Clown Tumbling\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/8uoZzj9vEww/mqdefault.jpg\",\n" +
                "            \"length\" : 1102\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"W5YwIWTzXiU\",\n" +
                "            \"name\" : \"Talking Tom and Friends 2 / Cartoon Games\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/W5YwIWTzXiU/mqdefault.jpg\",\n" +
                "            \"length\" : 826\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"c0BLr35bmTc\",\n" +
                "            \"name\" : \"Baby Doll Refrigerator toys and Pororo Food toys\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/c0BLr35bmTc/mqdefault.jpg\",\n" +
                "            \"length\" : 438\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"SXarF-Qh4Uc\",\n" +
                "            \"name\" : \"Sweet Baby Girl Christmas Fun 2 – Village & Winter Crafts of Santa\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/SXarF-Qh4Uc/mqdefault.jpg\",\n" +
                "            \"length\" : 438\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"2DI_G9uI0jE\",\n" +
                "            \"name\" : \"Top Ten Online Games\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/2DI_G9uI0jE/mqdefault.jpg\",\n" +
                "            \"length\" : 759\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"92-kIcS-8uI\",\n" +
                "            \"name\" : \"Kids Learn Safety | Lift Safety For Kids\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/92-kIcS-8uI/mqdefault.jpg\",\n" +
                "            \"length\" : 876\n" +
                "          }\n" +
                "        ]\n" +
                "     },\n" +
                "     {\n" +
                "        \"id\" : \"PLnG4P8mEMi0FsgdMMvk9QWT9kaeqzGrkB\",\n" +
                "        \"name\" : \"Cartoons\",\n" +
                "        \"thumb\" : \"https://i.ytimg.com/vi/aVPmaB41w3g/mqdefault.jpg\",\n" +
                "        \"videos\" : [\n" +
                "          {\n" +
                "              \"id\" : \"aVPmaB41w3g\",\n" +
                "            \"name\" : \"Tom And Jerry English Episodes - The Cat\\'s Me Ouch\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/aVPmaB41w3g/mqdefault.jpg\",\n" +
                "            \"length\" : 188\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"RjT9Vkds_e4\",\n" +
                "            \"name\" : \"Charlie Chaplin - Love Burger\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/RjT9Vkds_e4/mqdefault.jpg\",\n" +
                "            \"length\" : 358\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"wQb-rQMk-Hc\",\n" +
                "            \"name\" : \"Tom and Jerry | Episode 57 | Jerry\\'s Cousin (1951)\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/wQb-rQMk-Hc/mqdefault.jpg\",\n" +
                "            \"length\" : 255\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"k5zx-m1GdYo\",\n" +
                "            \"name\" : \"Charlie Chaplin - Mind The Back\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/k5zx-m1GdYo/mqdefault.jpg\",\n" +
                "            \"length\" : 348\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"4pEsb1pOEUw\",\n" +
                "            \"name\" : \"Tom And Jerry English Episodes - Jerry Cousin\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/4pEsb1pOEUw/mqdefault.jpg\",\n" +
                "            \"length\" : 770\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"vbkE8WicM5A\",\n" +
                "            \"name\" : \"Tom And Jerry English Episodes - Baby Puss\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/vbkE8WicM5A/mqdefault.jpg\",\n" +
                "            \"length\" : 180\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"PQ87jvuW8n4\",\n" +
                "            \"name\" : \"Tom And Jerry / Tiger Cat \",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/PQ87jvuW8n4/mqdefault.jpg\",\n" +
                "            \"length\" : 176\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"BiZdo7-5oW0\",\n" +
                "            \"name\" : \"Baby Hector - Tom the Tow Truck\\'s Car Wash\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/BiZdo7-5oW0/mqdefault.jpg\",\n" +
                "            \"length\" : 1382\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"OZ6CyS2fe_U\",\n" +
                "            \"name\" : \"Baby Panda\\'s Trapped on a Big Tree | Mushroom Fairies, Angry Bull\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/OZ6CyS2fe_U/mqdefault.jpg\",\n" +
                "            \"length\" : 560\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"hmm_ANty9TQ\",\n" +
                "            \"name\" : \"What is in Vending Machine? | Baby Panda\\'s Cool Car\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/hmm_ANty9TQ/mqdefault.jpg\",\n" +
                "            \"length\" : 1474\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"EjtVGVs7de0\",\n" +
                "            \"name\" : \"KFP - Secrets of the Furious Five - Tigress\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/EjtVGVs7de0/mqdefault.jpg\",\n" +
                "            \"length\" : 198\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"eqJ8OGB_2G8\",\n" +
                "            \"name\" : \"Tiger vs BOAR (Kung Fu Panda Secrets of cut Sub Indo)\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/eqJ8OGB_2G8/mqdefault.jpg\",\n" +
                "            \"length\" : 170\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"eKdffw2oFps\",\n" +
                "            \"name\" : \"Funny Animated cartoon for Kids | Cartoon Caillou | Caillou\\'s grounded\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/eKdffw2oFps/mqdefault.jpg\",\n" +
                "            \"length\" : 4253\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"kgAuNyuCxRI\",\n" +
                "            \"name\" : \"Bibi animals\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/kgAuNyuCxRI/mqdefault.jpg\",\n" +
                "            \"length\" : 473\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"vC6txeeWjXA\",\n" +
                "            \"name\" : \"Nature Cat\\'s Adventure - Cat makes baby animals\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/vC6txeeWjXA/mqdefault.jpg\",\n" +
                "            \"length\" : 623\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"mnvPDXfeQ_8\",\n" +
                "            \"name\" : \"Charlie Chaplin ᴴᴰ Mega Episode [13]\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/mnvPDXfeQ_8/mqdefault.jpg\",\n" +
                "            \"length\" : 2748\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"HmXQmGUwEDs\",\n" +
                "            \"name\" : \"Charlie Chaplin - The Haunted Hous\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/HmXQmGUwEDs/mqdefault.jpg\",\n" +
                "            \"length\" : 363\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"PsWnFMVvFlg\",\n" +
                "            \"name\" : \"Charlie Chaplin - Royal Shampooing\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/PsWnFMVvFlg/mqdefault.jpg\",\n" +
                "            \"length\" : 345\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"eEMKcgwPGdo\",\n" +
                "            \"name\" : \"Charlie Chaplin - Do Not Build It Yourself\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/eEMKcgwPGdo/mqdefault.jpg\",\n" +
                "            \"length\" : 398\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"GJFWH1E6HS4\",\n" +
                "            \"name\" : \"Charlie Chaplin - Chaplin 2.0\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/GJFWH1E6HS4/mqdefault.jpg\",\n" +
                "            \"length\" : 348\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"k_2yww9qSaQ\",\n" +
                "            \"name\" : \"Charlie Chaplin - Pretty Dummy\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/k_2yww9qSaQ/mqdefault.jpg\",\n" +
                "            \"length\" : 359\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"oQOXosxDvPc\",\n" +
                "            \"name\" : \"Charlie Chaplin - The Clumsy Nanny\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/oQOXosxDvPc/mqdefault.jpg\",\n" +
                "            \"length\" : 355\n" +
                "          },\n" +
                "          {\n" +
                "              \"id\" : \"TDfKc01CeeA\",\n" +
                "            \"name\" : \"Nina Needs To Go\",\n" +
                "            \"thumb\" : \"https://i.ytimg.com/vi/TDfKc01CeeA/mqdefault.jpg\",\n" +
                "            \"length\" : 1506\n" +
                "          }\n" +
                "        ]\n" +
                "     }\n" +
                "  ]\n" +
                "}";
        /*InputStream input = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL("https://raw.githubusercontent.com/Sayma7/garbage/master/.gitignore/data");
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                String msg = "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
                Log.e(TAG, msg);
                isOk = false;
                return msg;
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();

            byte data[] = new byte[4096];
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    input.close();
                    isOk = false;
                    return null;
                }
                P.appendData(YoutubePLayListerActivity.PL_DATA, new String(data));
            }
        } catch (Exception e) {
            e.printStackTrace();
            isOk = false;
            return null;
        } finally {
            try {
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        isOk = true;
        return P.getData(YoutubePLayListerActivity.PL_DATA);*/
    }

    @Override
    protected void onPostExecute(String fileData) {
        super.onPostExecute(fileData);
        if (isOk)
            apiCallback.onSuccess(fileData);
        else
            apiCallback.onError(fileData == null ? "List loading failed" : fileData);
    }
}