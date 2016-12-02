package uwi.dcit.AgriExpenseTT;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.widget.TextView;

import uwi.dcit.AgriExpenseTT.fragments.FragmentSelectLocation;
import uwi.dcit.AgriExpenseTT.helpers.DHelper;

public class SelectLocation extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_cycle);
//		setupInitialFrag();
	}
	
	public void replaceSub(int main, int sub) {
		((TextView)findViewById(R.id.tv_mainNew_header)).setText(main);
		((TextView)findViewById(R.id.tv_mainNew_subheader)).setText(sub);		
	}	
	
	public void setupInitialFrag() {
		replaceSub(R.id.tv_mainNew_header, R.id.tv_mainNew_subheader);
		
		Bundle arguments = new Bundle();
		arguments.putString("type", DHelper.location_country);
		
		Fragment listfrag = new FragmentSelectLocation();
		listfrag.setArguments(arguments);
		
		getSupportFragmentManager()
			.beginTransaction()
			.add(R.id.NewCycleListContainer,listfrag)
			.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_location, menu);
		return true;
	}
}
