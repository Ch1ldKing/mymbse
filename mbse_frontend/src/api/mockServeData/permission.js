import Mock from "mockjs";
import { getFileTree, sendToServer } from "..";

export default {
  getProjects: () => {
    return {
      code: 20000,
      data: {
        projects: [
          { id: 1, name: "Project 1", description: "Description of Project 1" },
          { id: 2, name: "Project 2", description: "Description of Project 2" },
          { id: 3, name: "Project 3", description: "Description of Project 3" },
        ],
      },
    };
  },

  getFiles: () => {
    return {
      code: 20000,
      resultData: {
        list: [
          { id: 1, name: "project1.txt", type: "text" },
          { id: 2, name: "Image.png", type: "image" },
          { id: 3, name: "a.mp4", type: "audio" },
        ],
      },
    };
  },

  getFileTree: (config) => {
    const val = config.body;
    if (val == 1)
      return {
        code: 20000,
        data: [
          {
            name: "一级 1",
            id: 1,
            children: [
              {
                name: "二级 1-1",
                id: 2,
                children: [
                  {
                    name: "三级 1-1-1",
                    id: 3,
                    file: true,
                    content:"Life is hard."
                  },
                ],
              },
            ],
          },
          {
            name: "一级 2",
            id: 4,
            children: [
              {
                name: "二级 2-1",
                id: 5,
                children: [
                  {
                    name: "三级 2-1-1",
                    id: 6,
                    file: true,
                    content:"I love you"
                  },
                ],
              },
              {
                name: "二级 2-2",
                id: 7,
                children: [
                  {
                    name: "三级 2-2-1",
                    id: 8,
                    file: true,
                    content:"You hate me"
                  },
                ],
              },
            ],
          },
          {
            name: "一级 3",
            id: 9,
            children: [
              {
                name: "二级 3-1",
                id: 10,
                children: [
                  {
                    name: "三级 3-1-1",
                    id: 11,
                    file: true,
                    content:"We are the best."
                  },
                ],
              },
              {
                name: "二级 3-2",
                id: 12,
                children: [
                  {
                    name: "三级 3-2-1",
                    id: 13,
                    file: true,
                    content:"Let's go."
                  },
                ],
              },
            ],
          },
        ],
      };
    if (val == 2)
      return {
        code: 20000,
        data: [
          {
            id: 3,
            name: "folder1-3",
            size: null,
            uploadTime: "2024-04-12T06:09:01.720+00:00",
            content: null,
            projectId: 1,
            parentId: null,
            children: [],
            file: false,
          },
          {
            id: 2,
            name: "folder1-2",
            size: null,
            uploadTime: "2024-04-12T06:02:10.926+00:00",
            content: null,
            projectId: 1,
            parentId: null,
            children: [
              {
                id: 8,
                name: "file2-1-2",
                size: 15,
                uploadTime: "2024-04-12T06:39:57.211+00:00",
                content: "The image mode shall consist of a science window.",
                projectId: 1,
                parentId: null,
                children: [],
                file: true,
              },
              {
                id: 7,
                name: "file2-1-1",
                size: 13,
                uploadTime: "2024-04-12T06:37:46.627+00:00",
                content:
                  "The DPU-BOOT CSC shall include a CPU BIT consisting of a branch test, a fixed point test, a floating point test, an interrupt test, a timer test, and a register test.",
                projectId: 1,
                parentId: null,
                children: [],
                file: true,
              },
            ],
            file: false,
          },
          {
            id: 1,
            name: "folder1-1",
            size: null,
            uploadTime: "2024-04-12T06:01:05.337+00:00",
            content: null,
            projectId: 1,
            parentId: null,
            children: [
              {
                id: 4,
                name: "file1-1-1",
                size: 10,
                uploadTime: "2024-04-12T06:37:31.953+00:00",
                content:
                  "The Bootstrap Monitor of DPU-BOOT CSC shall be activated if the Clear-To-Send (CTS) signal is active on the RS-232 interface.   If no activity is detected on the RS-232 interface within 30 seconds, the Bootstrap shall load a configuration of DPU-RTOS according to BC_INDEX in the SYSTEM_BLOCK.",
                projectId: 1,
                parentId: null,
                children: [],
                file: true,
              },
              {
                id: 6,
                name: "file1-1-3",
                size: 11,
                uploadTime: "2024-04-12T06:36:07.765+00:00",
                content: "This is a sysml model.",
                projectId: 1,
                parentId: null,
                children: [],
                file: true,
              },
              {
                id: 5,
                name: "file1-1-2",
                size: 12,
                uploadTime: "2024-04-12T06:35:48.932+00:00",
                content: "This is a sample text.",
                projectId: 1,
                parentId: null,
                children: [],
                file: true,
              },
            ],
            file: false,
          },
        ],
      };
  },

  getFileById: (config) => {
    const val = config.body;
    return {
      code: 20000,
      data: {
        "id": 4,
        "name": "file1-1-1",
        "size": 10,
        "uploadTime": "2024-04-12T06:37:31.953+00:00",
        "content": "The Bootstrap Monitor of DPU-BOOT CSC shall be activated if the Clear-To-Send (CTS) signal is active on the RS-232 interface.   If no activity is detected on the RS-232 interface within 30 seconds, the Bootstrap shall load a configuration of DPU-RTOS according to BC_INDEX in the SYSTEM_BLOCK.",
        "projectId": 1,
        "parentId": 1,
        "children": [],
        "file": true
    }
    };
  },

  getTypeFiles: (config) => {
    console.log(config.body);
    const val = JSON.parse(config.body);
    if (val.fileTypeInfo.name == 1)
      return {
        code: 20000,
        resultData: {
          list: [
            { ftiid: 1, name: "project1.txt", type: "text" },
            { ftiid: 2, name: "Image.png", type: "image" },
            { ftiid: 3, name: "a.mp4", type: "audio" },
          ],
        },
      };
    else
      return {
        code: 20000,
        resultData: {
          list: [
            { ftiid: 4, name: "project2.txt", type: "text" },
            { ftiid: 5, name: "Image2.png", type: "image" },
            { ftiid: 6, name: "b.mp4", type: "audio" },
          ],
        },
      };
  },

  sendToServer: (config) => {
    const data = JSON.parse(config.body);
    console.log("1",data.val1,data.val2);
    return {
      code: 20000,
      data: true,
    };
  },

  updateFileById: (config) => {
    console.log(config.body);
    //const data = JSON.parse(config.body);
    //console.log("2",data.val1,data.val2);
    return {
      code: 20000,
      data: true,
    };
  }
};
