package com.ci123.library.basiclib.utils;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.ci123.sdk.myutil.LogUtils;
import com.pocketdigi.utils.FLameUtils;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * AudioRecorder的工具类，从AudioRocoder录制raw文件，然后转成mp3<br/>
 * 依赖libs目录下的armeabi目录和flame.jar<br/>
 * 
 * 权限要求<br/>
 * 
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.RECORD_AUDIO" />
 *
 * 
 */
public class AudioRecorder2Mp3Util {
	/**
	 * 构造时候需要的Activity，主要用于获取文件夹的路径
	 */
	private Activity activity;

	/**
	 * 文件代号
	 */
	public static final int RAW = 0X00000001;
	public static final int MP3 = 0X00000002;

	/**
	 * 文件路径
	 */
	private String rawPath = null;
	private String mp3Path = null;

	/**
	 * 采样频率
	 */
	private static final int SAMPLE_RATE = 8000;

	/**
	 * 录音需要的一些变量
	 */
	private short[] mBuffer;
	private AudioRecord mRecorder;

	private StatusChangeListener listener;

	/**
	 * 构造方法
	 * 
	 * @param activity
	 */
	public AudioRecorder2Mp3Util(Activity activity) {
		this.activity = activity;
	}

	/**
	 * 录音状态
	 */
	private boolean isRecording = false;
	/**
	 * 是否转换ok
	 */
	private boolean convertOk = false;

	/**
	 * 
	 * @param activity
	 * @param rawPath
	 * @param mp3Path
	 */
	public AudioRecorder2Mp3Util(Activity activity, String rawPath,
			String mp3Path) {
		this.activity = activity;
		this.rawPath = rawPath;
		this.mp3Path = mp3Path;
	}

	/**
	 * 开始录音
	 */
	public boolean startRecording() {
		// 如果正在录音，则返回
		if (isRecording) {
			return isRecording;
		}
		// 初始化
		if (mRecorder == null) {
			initRecorder();
		}

		getFilePath();
		mRecorder.startRecording();
		LogUtils.i("start_record", rawPath);
		new Thread(new Runnable() {
			@Override
			public void run() {	//新开一个线程
				startBufferedWrite(new File(rawPath));
				converToMp3File();
			}
		}).start();

		isRecording = true;
		return isRecording;
	}

	/**
	 * 停止录音，并且转换文件,<br/>
	 * <b>这很可能是个耗时操作，建议在后台中做
	 *
	 * @return
	 */
	public boolean stopRecordingAndConvertFile() {
		if (!isRecording) {
			return isRecording;
		}

		// 停止
		mRecorder.stop();
		isRecording = false;

		// 开始转换
		FLameUtils lameUtils = new FLameUtils(1, SAMPLE_RATE, 96);

		convertOk = lameUtils.raw2mp3(rawPath, mp3Path);
		int i = 0;
		return isRecording ^ convertOk;// convertOk==true,return true
	}


	public void stopRecord(){
		if (!isRecording){
			return ;
		}
		mRecorder.stop();
		isRecording = false;
	}

	/**
	 * 获取文件的路径
	 * 
	 * @param fileAlias
	 *            RAW or MP3
	 * @return
	 */
	public String getFilePath(int fileAlias) {
		if (fileAlias == RAW) {
			return rawPath;
		} else if (fileAlias == MP3) {
			return mp3Path;
		} else
			return null;
	}

	public void cancelRecord(){
		if (!isRecording){
			return ;
		}
		mRecorder.stop();
		isRecording = false;
	}


	// /**
	// * 开启下一次录音前，reset一下
	// *
	// * @param cleanFlag
	// */
	// public void reset(int cleanFlag) {
	// cleanFile(cleanFlag);
	// }

	/**
	 * 清理文件
	 * 
	 * @param cleanFlag
	 *            RAW,MP3 or RAW|MP3
	 */
	public void cleanFile(int cleanFlag) {
		File f = null;
		try {
			switch (cleanFlag) {
			case MP3:
				f = new File(mp3Path);
				if (f.exists())
					f.delete();
				break;
			case RAW:
				f = new File(rawPath);
				if (f.exists())
					f.delete();
				break;
			case RAW | MP3:
				f = new File(rawPath);
				if (f.exists())
					f.delete();
				f = new File(mp3Path);
				if (f.exists())
					f.delete();
				break;
			}
			f = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭,可以先调用cleanFile来清理文件
	 */
	public void close() {
		if (mRecorder != null)
			mRecorder.release();
		activity = null;
	}

	public String getMP3Path(){
		return mp3Path;
	}

	public String getRawPath(){
		return rawPath;
	}

	//根据mp3Path，获取当前音频文件的大小
	public Long getMP3FileSize(){
		long mSize = 0;
		if (mp3Path != null) {
			File mFile = new File(mp3Path);
			if (mFile.exists()){
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(mFile);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				try {
					mSize = fis.available();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			LogUtils.i("mp3_file_size", String.valueOf(mSize));
		}
		return mSize;
	}

	//根据mp3Path，获取当前音频文件的文件名
	public String getMP3FileName(){
		String mFileName = null;
		if (mp3Path != null) {
			File mFile = new File(mp3Path);
			mFileName = mFile.getName();
		}
		return mFileName;
	}

	//重命名mp3文件
	public void setMp3FileName(String fileName) {
		if(mp3Path != null){
			File mfile = new File(mp3Path);
			mp3Path = mp3Path.substring(0, mp3Path.lastIndexOf("/")+1)+fileName+mp3Path.substring(mp3Path.lastIndexOf("."), mp3Path.length());
			File newFile = new File(mp3Path);
			mfile.renameTo(newFile);
		}
	}

	// -------内部的一些工具方法-------
	/**
	 * 初始化
	 */
	private void initRecorder() {
		int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE,
				AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
		mBuffer = new short[bufferSize];
		mRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE,
				AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT,
				bufferSize);
	}

	/**
	 * 设置路径，第一个为raw文件，第二个为mp3文件
	 * 
	 * @return
	 */
	private void getFilePath() {
		try {
			String folder = "audio_recorder_2_mp3";
			String fileName = String.valueOf(System.currentTimeMillis());
			if (rawPath == null) {
				File raw = new File(activity.getDir(folder,
						activity.MODE_PRIVATE), fileName + ".raw");
				raw.createNewFile();
				rawPath = raw.getAbsolutePath();
				raw = null;
			}
			if (mp3Path == null) {
				File mp3 = new File(activity.getDir(folder,
						activity.MODE_PRIVATE), fileName + ".mp3");
				mp3.createNewFile();
				mp3Path = mp3.getAbsolutePath();
				mp3 = null;
			}

			Log.d("rawPath", rawPath);
			Log.d("mp3Path", mp3Path);

			runCommand("chmod 777 " + rawPath);
			runCommand("chmod 777 " + mp3Path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行cmd命令，并等待结果
	 * 
	 * @param command
	 *            命令
	 * @return 是否成功执行
	 */
	private boolean runCommand(String command) {
		boolean ret = false;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(command);
			process.waitFor();
			ret = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				process.destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	/**
	 * 写入到raw文件
	 * 
	 * @param file
	 */
	private void startBufferedWrite(final File file) {
				DataOutputStream output = null;
				try {
					output = new DataOutputStream(new BufferedOutputStream(
							new FileOutputStream(file)));
					while (isRecording) {
						int readSize = mRecorder.read(mBuffer, 0,
								mBuffer.length);
						for (int i = 0; i < readSize; i++) {
							output.writeShort(mBuffer[i]);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (output != null) {
						try {
							output.flush();
						} catch (IOException e) {
							e.printStackTrace();

						} finally {
							try {
								output.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
	}

	//实时地将raw文件转化为mp3文件
	private void converToMp3File() {
		FLameUtils lameUtils = new FLameUtils(1, SAMPLE_RATE, 96);
		convertOk = lameUtils.raw2mp3(rawPath, mp3Path);
		if (listener != null){
			listener.onConvertFinish(convertOk, convertOk? mp3Path:"");
		}
	}

	public void setListener(StatusChangeListener listener) {
		this.listener = listener;
	}

	public interface StatusChangeListener{
		void onConvertFinish(boolean success, String path);
	}

	/**
	 * 例子
	 *
	 * @author john
	 *
	 */
	class Sample {
		AudioRecorder2Mp3Util util = null;
		String mp3 = null;
		String raw = null;

		void start() {
			// util=new AudioRecorder2Mp3Util(activity); 一般是用这个
			if (util == null)
				util = new AudioRecorder2Mp3Util(null, "/sdcard/sample.raw",
						"/sdcard/sample.mp3");
			// 如果不为空，则表示运行过。可以清理 当然也可以不清理，看需求而定
			if (mp3 != null || raw != null) {
				util.cleanFile(RAW | MP3);
			}
			util.startRecording();
		}

		void stop() {
			/*boolean isOK = util.stopRecordingAndConvertFile();
			// 获得生成的文件路径
			raw = util.getFilePath(RAW);
			mp3 = util.getFilePath(MP3);
			// 清理掉源文件
			util.cleanFile(RAW);

			util.close();*/
		}
	}
}
