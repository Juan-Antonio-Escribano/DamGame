package dam.gala.damgame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.damgame.R;

import java.util.List;

import dam.gala.damgame.model.Score;

public class RankingAdapter extends BaseAdapter {
    private Context context;
    private List<Score> items;
    private TextView tvNameUser, tvScore, tvPosition;
    private LinearLayout lyRankingMain;

    public RankingAdapter(Context context, List<Score> items){
        this.context=context;
        this.items=items;
    }
    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(R.layout.list_adapter,null);

        this.tvNameUser=convertView.findViewById(R.id.tvNameUser);
        this.tvPosition=convertView.findViewById(R.id.tvPosition);
        this.tvScore=convertView.findViewById(R.id.tvScore);
        this.lyRankingMain=convertView.findViewById(R.id.lyRankingMain);

        this.tvNameUser.setText(this.items.get(position).getNameUser());
        this.tvPosition.setText(String.valueOf(this.items.get(position).getRankingPosition()));
        this.tvScore.setText("Puntos: "+String.valueOf(this.items.get(position).getRankingScore()));
        this.lyRankingMain.setBackground(convertView.getResources().getDrawable(R.drawable.ranking_main));

        switch (position){
            case 0:
                tvPosition.setBackground(convertView.getResources().getDrawable(R.drawable.position_1));
                break;
            case 1:
                tvPosition.setBackground(convertView.getResources().getDrawable(R.drawable.position_2));
                break;
            case 2:
                tvPosition.setBackground(convertView.getResources().getDrawable(R.drawable.position_3));
                break;

            default:
                tvPosition.setBackground(convertView.getResources().getDrawable(R.drawable.position ));
                break;
        }
        return convertView;
    }


}
