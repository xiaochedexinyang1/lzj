package com.py.ysl.bean;

import java.util.List;

/**
 * Created by liyuan on 2016/10/26.
 * 版本更新的实体类
 */
public class UpdataVerCode {


	/**
	 * data : {"list":[{"desc":"5.3.1","downloadurl":"http://images.aiyingshi.com/android/Aiyingshi79.apk","ismandatory":0,"no":79,"updatedesc":"1.更新新版本","updatetitle":"版本更新"}],"totalcount":1}
	 * message : 正常获取数据
	 * returncode : 0
	 */

	private DataBean data;
	private String message;
	private String returncode;

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReturncode() {
		return returncode;
	}

	public void setReturncode(String returncode) {
		this.returncode = returncode;
	}

	public static class DataBean {
		/**
		 * list : [{"desc":"5.3.1","downloadurl":"http://images.aiyingshi.com/android/Aiyingshi79.apk","ismandatory":0,"no":79,"updatedesc":"1.更新新版本","updatetitle":"版本更新"}]
		 * totalcount : 1
		 */

		private int totalcount;
		private List<ListBean> list;

		public int getTotalcount() {
			return totalcount;
		}

		public void setTotalcount(int totalcount) {
			this.totalcount = totalcount;
		}

		public List<ListBean> getList() {
			return list;
		}

		public void setList(List<ListBean> list) {
			this.list = list;
		}

		public static class ListBean {
			/**
			 * desc : 5.3.1
			 * downloadurl : http://images.aiyingshi.com/android/Aiyingshi79.apk
			 * ismandatory : 0
			 * no : 79
			 * updatedesc : 1.更新新版本
			 * updatetitle : 版本更新
			 */

			private String desc;
			private String downloadurl;
			private int ismandatory;
			private int no;
			private String updatedesc;
			private String updatetitle;

			public String getDesc() {
				return desc;
			}

			public void setDesc(String desc) {
				this.desc = desc;
			}

			public String getDownloadurl() {
				return downloadurl;
			}

			public void setDownloadurl(String downloadurl) {
				this.downloadurl = downloadurl;
			}

			public int getIsmandatory() {
				return ismandatory;
			}

			public void setIsmandatory(int ismandatory) {
				this.ismandatory = ismandatory;
			}

			public int getNo() {
				return no;
			}

			public void setNo(int no) {
				this.no = no;
			}

			public String getUpdatedesc() {
				return updatedesc;
			}

			public void setUpdatedesc(String updatedesc) {
				this.updatedesc = updatedesc;
			}

			public String getUpdatetitle() {
				return updatetitle;
			}

			public void setUpdatetitle(String updatetitle) {
				this.updatetitle = updatetitle;
			}
		}
	}
}
