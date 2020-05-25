
package com.forezp.web;

import com.forezp.annotation.SysLogger;
import com.forezp.dto.BlogDetailDTO;
import com.forezp.dto.RespDTO;
import com.forezp.entity.Blog;
import com.forezp.exception.CommonException;
import com.forezp.exception.ErrorCode;
import com.forezp.service.BlogService;
import com.forezp.util.UserUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

	@Autowired
	private BlogService blogService;

	@Value("${server.port}")
	private String port;

	@SuppressWarnings("unchecked")
	@ApiOperation(value="发布博客", notes="发布博客")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("")
	@SysLogger("postBlog")
	public RespDTO<Blog> postBlog(@RequestBody Blog blog) {
		// 字段判读省略
		Blog blog1 = blogService.postBlog(blog);
		return RespDTO.onSuc(blog1);
	}

	@SuppressWarnings("unchecked")
	@ApiOperation(value="根据用户id获取所有的blog", notes="根据用户id获取所有的blog")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/{username}")
	@SysLogger("getBlogs")
	public RespDTO<List<Blog>> getBlogs(@PathVariable String username) {
		// 字段判读省略
		if (UserUtils.isMyself(username)) {
			List<Blog> blogs = blogService.findBlogs(username);
			return RespDTO.onSuc(blogs);
		} else {
			throw new CommonException(ErrorCode.TOKEN_IS_NOT_MATCH_USER);
		}
	}

	@SuppressWarnings("unchecked")
	@ApiOperation(value="获取博文的详细信息", notes="获取博文的详细信息")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/{id}/detail")
	@SysLogger("getBlogDetail")
	public RespDTO<BlogDetailDTO> getBlogDetail(@PathVariable Long id) {
		BlogDetailDTO blogDetailDTO = blogService.findBlogDetail(id);
		return RespDTO.onSuc(blogDetailDTO, port);
	}
}