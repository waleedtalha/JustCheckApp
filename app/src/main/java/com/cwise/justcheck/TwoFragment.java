package com.cwise.justcheck;

import static android.content.Context.MODE_PRIVATE;

import static com.cwise.justcheck.OneFragment.mediaFiles;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TwoFragment extends Fragment {
    RecyclerView recyclerView;
    public VideoFilesAdapter videoAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_two, container, false);
        recyclerView = view.findViewById(R.id.filesRV);
//        SharedPreferences prefs = getActivity().getSharedPreferences((String) MY_PREFS_NAME,MODE_PRIVATE);
//        int position = prefs.getInt("itemposition", 0);
        if (mediaFiles != null && mediaFiles.size() > 0) {

            videoAdapter = new VideoFilesAdapter(mediaFiles, getContext());
            recyclerView.setAdapter(videoAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                    RecyclerView.VERTICAL, false));
            videoAdapter.notifyDataSetChanged();
        }
        return view;
    }

//    public ArrayList<MediaFiles> getAllVideos(Context context) {
//        ArrayList<MediaFiles> tempMediaFiles = new ArrayList<>();
//        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//        String[] projection = {
//                MediaStore.Video.Media._ID,
//                MediaStore.Video.Media.DATA,
//                MediaStore.Video.Media.TITLE,
//                MediaStore.Video.Media.SIZE,
//                MediaStore.Video.Media.DATE_ADDED,
//                MediaStore.Video.Media.DURATION,
//                MediaStore.Video.Media.DISPLAY_NAME
//        };
//        Cursor cursor = context.getContentResolver().query(uri, projection,
//                null, null, null);
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                String id = cursor.getString(0);
//                String path = cursor.getString(1);
//                String title = cursor.getString(2);
//                String size = cursor.getString(3);
//                String dateAdded = cursor.getString(4);
//                String duration = cursor.getString(5);
//                String fileName = cursor.getString(6);
//                MediaFiles videoFiles = new MediaFiles(id, path, title, fileName, size
//                        , dateAdded, duration);
//                // to check the files are present
//                Log.e("Path", path);
//                int slashFirstIndex = path.lastIndexOf("/");
//                String subString = path.substring(0, slashFirstIndex);
//                // /storage/sd_card/videos/video.mp4
////                int index = subString.lastIndexOf("/");
////                String folderName = subString.substring(index + 1,slashFirstIndex);
//                if (!folderList.contains(subString))
//                    folderList.add(subString);
//                tempMediaFiles.add(videoFiles);
//            }
//            cursor.close();
//        }
//        return tempVideoFiles;
//    }


}