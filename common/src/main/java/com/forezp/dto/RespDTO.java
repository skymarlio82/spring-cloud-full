
package com.forezp.dto;

import java.io.Serializable;

public class RespDTO<T> implements Serializable {

	private static final long serialVersionUID = 422036691190193521L;

	public int code = 0;
	public String error = "";
	public T data;
	public String port;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static RespDTO onSuc(Object data) {
		RespDTO resp = new RespDTO();
		resp.data = data;
		return resp;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static RespDTO onSuc(Object data, String port) {
		RespDTO resp = new RespDTO();
		resp.data = data;
		resp.port = port;
		return resp;
	}

	@Override
	public String toString() {
		return "RespDTO{" + "code=" + code + ", error='" + error + '\'' + ", data=" + data + '}';
	}
}