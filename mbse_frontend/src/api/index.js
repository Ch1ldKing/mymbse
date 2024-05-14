import http from "@/utils/request";

export const getProjects = () => {
  return http.get("/projects/");
};

export const getFiles = () => {
  return http.get("/permission/getFiles");
};

export const getFileTree = (data) => {
  return http.get(`/projects/${data}/structure`);
};

export const getFileById = (data) => {
  return http.get(`/fileorfolder/${data}`);
}


export const getTypeFiles = (data) => {
  return http.get("/fileorfolder/", data);
}


export const sendToServer = (id,content) => {
  return http.post("/permission/sendToServer", { id, content });
}

export const updateFileById=(id,content) => {
  console.log({id,content});
return http.post("/fileorfolder/updateContent",{id,content});
}


export const getSpecialFiles = (content) => {
  console.log({content});
  return http.post("/fileorfolder/search",{content});

}