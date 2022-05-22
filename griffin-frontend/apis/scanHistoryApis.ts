import axios from "axios";

const instance = axios.create({
  // this is the old test one
  // baseURL: 'http://52.65.89.122/',

  // this is the new one
  baseURL: 'http://52.65.89.122:80/',
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