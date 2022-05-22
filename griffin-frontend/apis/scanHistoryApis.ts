import axios from "axios";

const instance = axios.create({
  // If the remote server is down, run it locally and use this:
  // baseURL: 'http://woodruffhosting.com:80/',

  // Otherwise, use https to connect to the server:
  baseURL: 'https://woodruffhosting.com:443/',
});

export async function readScanHistory() {
  // old
  // return await instance.get("test/simple");
  // new
  return await instance.get("before");

}

export async function readUpdatedScanHistory() {
  // old
  // return await instance.get("test/simple");

  // new
  return await instance.get("after");
}