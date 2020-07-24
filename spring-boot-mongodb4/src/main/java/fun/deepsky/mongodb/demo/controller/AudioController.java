package fun.deepsky.mongodb.demo.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import fun.deepsky.mongodb.demo.gridfs.GridfsService;


@RestController
public class AudioController {

	@Autowired
	GridfsService gridfsService;

	@RequestMapping(value = "/file/upload")
	public Object uploadData(@RequestParam(value = "file") MultipartFile file) {

		GridFSInputFile gridFSInputFile = gridfsService.save(file);

		if (gridFSInputFile == null) {
			return "upload fail";
		}
		String id = gridFSInputFile.getId().toString();
		String md5 = gridFSInputFile.getMD5();
		String name = gridFSInputFile.getFilename();
		long length = gridFSInputFile.getLength();

		Map<String, Object> dt = new HashMap<String, Object>();
		dt.put("id", id);
		dt.put("md5", md5);
		dt.put("name", name);
		dt.put("length", length);

		return dt;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/file/delete",method=RequestMethod.POST)
	public Object deleteFile(@RequestParam(value = "id") String id) {
		gridfsService.remove(id);
		return "ok";
	}

	  /**
     * 下载文件
     * @param id
     * @param response
     */
    @RequestMapping(value = "/file/{id}", method = RequestMethod.GET)
    public void getFile(@PathVariable String id, HttpServletResponse response) {
        GridFSDBFile file = gridfsService.getById(new ObjectId(id));

        if (file == null) {
            responseFail("404 not found",response);
            return;
        }

        OutputStream os = null;

        try {
            os = response.getOutputStream();
            response.addHeader("Content-Disposition", "attachment;filename=" + file.getFilename());
            response.addHeader("Content-Length", "" + file.getLength());
            response.setContentType("application/octet-stream");
            file.writeTo(os);
            os.flush();
            os.close();

        } catch (Exception e) {
            try{
                if (os != null) {
                    os.close();
                }
            }catch (Exception e2){}
            e.printStackTrace();
        }
    }
	@RequestMapping(value = "/file/view/{id}", method = RequestMethod.GET)
    public void viewFile(@PathVariable String id, HttpServletResponse response) {

        GridFSDBFile file = gridfsService.getById(new ObjectId(id));

        if (file == null) {
            responseFail("404 not found",response);
            return;
        }

        OutputStream os = null;

        try {
            os = response.getOutputStream();
            response.addHeader("Content-Disposition", "attachment;filename=" + file.getFilename());
            response.addHeader("Content-Length", "" + file.getLength());
            response.setContentType(file.getContentType());
            file.writeTo(os);
            os.flush();
            os.close();

        } catch (Exception e) {
            try{
                if (os != null) {
                    os.close();
                }
            }catch (Exception e2){}
            e.printStackTrace();
        }

    }

	/**
	 * 失败回写
	 * @param msg
	 * @param response
	 */
	private void responseFail(String msg, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			String res = mapper.writeValueAsString(msg);
			out = response.getWriter();
			out.append(res);
		} catch (IOException e) {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e2) {
			}
		}
	}

}
