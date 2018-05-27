package local.hal.st32.android.preferencesample;

/*
No.8 プレファレンス
お品書き
1.Androidのデータ保存
2.保存と削除
3.取得
[1] Androidのデータ保存
Androidのデータ保存方法は以下の5種
・SQLiteデータベース
・プレファレンス
→簡易データをキーと値のペアで保存
・内部ストレージ
→端末内部にファイル形式で保存
・外部ストレージ
→SDカードなどにファイル形式で保存。最近はない端末が多い
・クラウド
→ネットワーク経由でサーバに保存。Webインターフェース(サーバサイド、Webアプリ)が別に必要
アプリの見た目や動作の設定値の保存にはDBを使わなくても、プレファレンスを使うと便利

[2]保存と削除
プレファレンスにデータを保存、削除するには以下の手順
1)プレファレンスオブジェクトを取得する
SharedPreference setting = getSharedPreferences(...,0);

2)エディタを取得する
SharedPreferences.Editor editor = setting.edit();

3)データを保存、削除
・保存
editor.put("...",....);
・削除
editor.put("...");

4)コミットする
editor.commit();
→コミットされるまでデータは反映されない

注)
処理モードはAPI17まではMODE_PRIVATEの他にMODE_MULTI.PROCESS, MODE_WORLD_WRITABLE,
MODE_MULTI_PROCESS, MODE_WORLD_WRITABLE, MODE_WORLD_READABLEから選ぶことができた
現在はMODE_PRIVATE以外非推奨になったのでMODE_PRIVATEを表す0をそのまま記述することが多い

[3]
プレファレンスに保存したデータを取得するには以下の手順
1)プレファレンスオブジェクトを取得する
→データ保存と同じ

2)データを取得する
... = setting.get〇〇("...",...);
 */


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * プレファレンス
 *
 * 画面表示用アクティビティクラス。
 */
public class PreferenceSampleActivity extends AppCompatActivity {
    /**
     * プレファレンスファイル名を表す定数フィールド。
     */
    private static final String PREFS_NAME = "PSPrefsFile";
    /**
     * 表示フォントでデフォルトを表す数値の定数フィールド。
     */
    private static final int FONT_TYPE_DEFAULT = 0;
    /**
     * 表示フォントで明朝体を表す数値の定数フィールド。
     */
    private static final int FONT_TYPE_SERIF = 1;
    /**
     * 表示フォントでゴシック体を表す数値の定数フィールド。
     */
    private static final int FONT_TYPE_SANS_SERIF = 2;
    /**
     *表示フォントで等幅を表す数値の定数フィールド。
     */
    private static final int FONT_TYPE_MONOSPACE = 3;
    /**
     * 表示フォントを表すフィールド。
     */
    private Typeface _fontType;
    /**
     * 表自体を表すフィールド。
     */
    private int _fontStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_sample);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        _fontStyle = settings.getInt("fontStyle",Typeface.NORMAL);

        int fontTypeCode = settings.getInt("fontType",FONT_TYPE_DEFAULT);
        _fontType = fontTypeInt2FontType(fontTypeCode);

        TextView tvSpeech = findViewById(R.id.tvSpeech);
        tvSpeech.setTypeface(_fontType,_fontStyle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.preference_sample,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menuFonttypeSerif:
                editor.putInt("fontType",FONT_TYPE_SERIF);
                _fontType = Typeface.SERIF;
                break;
            case R.id.menuFonttypeSunsserif:
                editor.putInt("fontType",FONT_TYPE_SANS_SERIF);
                _fontType = Typeface.SANS_SERIF;
                break;
            case R.id.menuFonttypeMonospace:
                editor.putInt("fontType",FONT_TYPE_MONOSPACE);
                _fontType = Typeface.MONOSPACE;
                break;
            case R.id.menuFontstyleNormal:
                editor.putInt("fontStyle",Typeface.NORMAL);
                _fontStyle = Typeface.NORMAL;
                break;
            case R.id.menuFontstyleItalic:
                editor.putInt("fontStyle",Typeface.ITALIC);
                _fontStyle = Typeface.ITALIC;
                break;
            case R.id.menuFontstyleBold:
                editor.putInt("fontStyle",Typeface.BOLD);
                _fontStyle = Typeface.BOLD;
                break;
            case R.id.menuFontstyleBolditalic:
                editor.putInt("fontStyle",Typeface.BOLD_ITALIC);
                _fontStyle = Typeface.BOLD_ITALIC;
                break;
            case R.id.menuReset:
                _fontType = Typeface.DEFAULT;
                editor.putInt("fontType",FONT_TYPE_DEFAULT);
                _fontStyle = Typeface.NORMAL;
                editor.putInt("fontStyle",Typeface.NORMAL);
                break;
        }
        editor.commit();

        TextView tvSpeech = findViewById(R.id.tvSpeech);
        tvSpeech.setTypeface(_fontType,_fontStyle);
        return super.onOptionsItemSelected(item);
    }

    /**
     * 表示フォントを表す数値をそれが該当するTypefaceオブジェクトに変換するメソッド。
     *
     * @param fontTypeInt 表示フォントを表す数値。このクラスの定数を使用する。
     * @return 該当するTypefaceオブジェクト。
     */
    private Typeface fontTypeInt2FontType(int fontTypeInt){
        Typeface fontType = Typeface.DEFAULT;
        switch (fontTypeInt){
            case FONT_TYPE_SERIF:
                fontType = Typeface.SERIF;
                break;
            case FONT_TYPE_SANS_SERIF:
                fontType = Typeface.SANS_SERIF;
                break;
            case FONT_TYPE_MONOSPACE:
                fontType = Typeface.MONOSPACE;
                break;
        }
        return fontType;
    }
}
