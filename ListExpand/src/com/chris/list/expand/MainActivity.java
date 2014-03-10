package com.chris.list.expand;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MainActivity extends Activity {

	private static final String TAG = "ChrisExpand";
	private ListView mListView;
	private CustomListAdapter mAdapter;
	private List<AppInfo> mList = new ArrayList<AppInfo>();
	
	private int mLcdWidth = 0;
	private float mDensity = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getSystemInfo();
		mAdapter = new CustomListAdapter(this);
		mListView = (ListView) findViewById(R.id.lvMain);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int pos,
					long arg3) {
				Log.d(TAG, "onItemClick");
				View footer = v.findViewById(R.id.footer);
				footer.startAnimation(new ViewExpandAnimation(footer));
				if (pos == mListView.getAdapter().getCount() - 1) {
					mListView.setSelection(mListView.getAdapter().getCount() - 1);
				}
			}
		});
		
		addItems();
	}
	
	private void getSystemInfo(){
		DisplayMetrics dm = getResources().getDisplayMetrics();
		mLcdWidth = dm.widthPixels;
		mDensity = dm.density;
	}
	
	private void addItems(){
		for(int i = 0; i < 30; i ++){
			AppInfo ai = new AppInfo();
			ai.appIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
			
			int j = i + 1;
			ai.appName = "应用Demo_" + j;
			ai.appVer = "版本: " + (j%10+1) + "." + (j%8+2) + "." + (j%6+3);
			ai.appSize = "大小: " + j*10 + "MB";
			mList.add(ai);
		}
		mAdapter.notifyDataSetChanged();
	}
	
	public class AppInfo{
		private Bitmap appIcon;
		private String appName;
		private String appVer;
		private String appSize;
		
		public AppInfo(){
			
		}
		public Bitmap getAppIcon() {
			return appIcon;
		}
		public void setAppIcon(Bitmap appIcon) {
			this.appIcon = appIcon;
		}
		public String getAppName() {
			return appName;
		}
		public void setAppName(String appName) {
			this.appName = appName;
		}
		public String getAppVer() {
			return appVer;
		}
		public void setAppVer(String appVer) {
			this.appVer = appVer;
		}
		public String getAppSize() {
			return appSize;
		}
		public void setAppSize(String appSize) {
			this.appSize = appSize;
		}
	}
	
	public class ViewHolder{
		private ImageView ivImage;
		private TextView tvName;
		private TextView tvVer;
		private TextView tvSize;
		
		public ImageView getIvImage() {
			return ivImage;
		}
		public void setIvImage(ImageView ivImage) {
			this.ivImage = ivImage;
		}
		public TextView getTvName() {
			return tvName;
		}
		public void setTvName(TextView tvName) {
			this.tvName = tvName;
		}
		public TextView getTvVer() {
			return tvVer;
		}
		public void setTvVer(TextView tvVer) {
			this.tvVer = tvVer;
		}
		public TextView getTvSize() {
			return tvSize;
		}
		public void setTvSize(TextView tvSize) {
			this.tvSize = tvSize;
		}
	}
	
	public class CustomListAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		public CustomListAdapter(Context context){
			mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mList.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView == null){
				convertView = mInflater.inflate(R.layout.expand_item, null);
				holder = new ViewHolder();
				holder.ivImage = (ImageView) convertView.findViewById(R.id.ivIcon);
				holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
				holder.tvVer = (TextView) convertView.findViewById(R.id.tvVer);
				holder.tvSize = (TextView) convertView.findViewById(R.id.tvSize);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			
			AppInfo ai = mList.get(position);
			holder.ivImage.setImageBitmap(ai.appIcon);
			holder.tvName.setText(ai.appName);
			holder.tvVer.setText(ai.appVer);
			holder.tvSize.setText(ai.appSize);
			
			// resize the button width and margin
			int btnWidth = (int) ((mLcdWidth - 20 - 10 * mDensity) / 3);
			RelativeLayout.LayoutParams lp = null;
			
			Button btnOpen = (Button) convertView.findViewById(R.id.btnOpen);
			lp = (RelativeLayout.LayoutParams) btnOpen.getLayoutParams();
			lp.width = btnWidth;
			btnOpen.setLayoutParams(lp);
			btnOpen.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					Toast.makeText(getApplicationContext(), "打开应用!", Toast.LENGTH_SHORT).show();
				}
			});
			
			Button btnView = (Button) convertView.findViewById(R.id.btnView);
			lp = (RelativeLayout.LayoutParams) btnView.getLayoutParams();
			lp.width = btnWidth;
			lp.leftMargin = 10;
			btnView.setLayoutParams(lp);
			btnView.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					Toast.makeText(getApplicationContext(), "查看详情!", Toast.LENGTH_SHORT).show();
				}
			});
			
			Button btnWarning = (Button) convertView.findViewById(R.id.btnWarning);
			lp = (RelativeLayout.LayoutParams) btnWarning.getLayoutParams();
			lp.width = btnWidth;
			lp.leftMargin = 10;
			btnWarning.setLayoutParams(lp);
			btnWarning.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					Toast.makeText(getApplicationContext(), "举报应用!", Toast.LENGTH_SHORT).show();
				}
			});
			
			// get footer height
			RelativeLayout footer = (RelativeLayout) convertView.findViewById(R.id.footer);
			int widthSpec = MeasureSpec.makeMeasureSpec((int) (mLcdWidth - 10 * mDensity), MeasureSpec.EXACTLY);
			footer.measure(widthSpec, 0);
			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) footer.getLayoutParams();
			params.bottomMargin = -footer.getMeasuredHeight();
			footer.setVisibility(View.GONE);
			
			return convertView;
		}
		
	}
}
