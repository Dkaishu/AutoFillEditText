# AutoFillEditText
To get AutoFillEditText into your build:

Step 1.Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
Step 2. Add the dependency

	dependencies {
	        compile 'com.github.Dkaishu:AutoFillEditText:v1.0.1'
	}
  
  Step 3. Use AutoFillEditText in XMl file
  
      <com.tincher.autofilledittext.AutoFillEditText
        android:id="@+id/my_edit_text"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"

        app:tag="test"
        app:autoFill="true"
        app:cleanerEnable= "true"

        app:paddingTop="10dp"
        app:paddingBottom="10dp"
        app:paddingLeft="15dp"
        android:paddingRight="15dp"

        app:cleanerSize="20dp"
        app:dropDownSize="25dp"

        app:hint="777"
        app:defaultText="66666"
        app:textColor="@color/colorPrimary"
        app:textSize="12sp"
        >
      </com.tincher.autofilledittext.AutoFillEditText>
    
    And then get/set the text in your java file
    
        AutoFillEditText myEditText  = (AutoFillEditText) findViewById(R.id.my_edit_text);
        myEditText.setText("that is it !");
        myEditText.getText();
        
