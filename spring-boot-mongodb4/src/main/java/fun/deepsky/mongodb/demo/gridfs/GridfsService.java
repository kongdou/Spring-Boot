package fun.deepsky.mongodb.demo.gridfs;

import java.io.InputStream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

@Service
public class GridfsService {

	@Autowired
	MongoDbFactory mongoDbFactory;

	/**
	 * 保存
	 * @param file
	 * @return
	 */
	public GridFSInputFile save(MultipartFile file) {
		GridFS gridFS = new GridFS((DB) mongoDbFactory.getDb());
		try {
			InputStream in = file.getInputStream();
			String name = file.getOriginalFilename();
			GridFSInputFile gridFSInputFile = gridFS.createFile(in);
			gridFSInputFile.setFilename(name);
			gridFSInputFile.setContentType(file.getContentType());
			gridFSInputFile.save();
			return gridFSInputFile;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 据id返回文件
	 */
	public GridFSDBFile getById(ObjectId id) {
		GridFS gridFS = new GridFS((DB) mongoDbFactory.getDb());
		return gridFS.findOne(new BasicDBObject("_id", id));
	}
	
	/**
	 * 删除文件
	 * @param id
	 */
	public void remove(String id) {
		GridFS gridFS = new GridFS((DB) mongoDbFactory.getDb());
		gridFS.remove(new ObjectId(id));
	}
}
