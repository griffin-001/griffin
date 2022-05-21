import axios from "axios";

const instance = axios.create({
  baseURL: 'http://52.65.89.122/',
});

export async function readScanHistory() {
  return await instance.get("test/simple"); // todo
}

export async function readUpdatedScanHistory() {
  return await instance.get("test/simple"); // todo
}