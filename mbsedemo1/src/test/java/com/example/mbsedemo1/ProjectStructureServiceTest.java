package com.example.mbsedemo1;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.mbsedemo1.Entity.FileorFolder;
import com.example.mbsedemo1.Mapper.FileorFolderMapper;
import com.example.mbsedemo1.Service.ProjectStructureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;

class ProjectStructureServiceTest {

    @Mock
    private FileorFolderMapper fileorfolderMapper;

    @InjectMocks
    private ProjectStructureService projectStructureService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
    /**
     * 测试 {@link ProjectStructureService#getProjectStructure(Integer)} 方法以验证其能正确返回项目结构。
     * 本测试案例模拟了从数据库获取顶层文件或文件夹节点及其子节点的场景。
     *
     * <p>步骤包括：
     * <ul>
     *   <li>配置 {@code FileorFolderMapper} 来返回特定的文件夹节点。</li>
     *   <li>调用服务层方法来获取整个文件结构。</li>
     *   <li>验证返回的结构中包含正确的节点和子节点。</li>
     *   <li>确认正确的数据库方法被调用，并且调用次数符合预期。</li>
     * </ul>
     *
     * <p>测试确保在给定顶层节点存在时，服务能够递归地加载所有子节点，并将这些节点组织成一个完整的项目结构列表。
     *
     * @throws Exception 如果在测试执行中发生异常
     */
    @Test
    void testGetProjectStructure() {
        // Arrange
        Integer projectId = 1;
        FileorFolder root = new FileorFolder();
        root.setId(1);
        FileorFolder child = new FileorFolder();
        child.setId(2);
        root.setChildren(Arrays.asList(child));

        when(fileorfolderMapper.findUpFolder(projectId)).thenReturn(Arrays.asList(root));
        when(fileorfolderMapper.findByParentId(1)).thenReturn(Arrays.asList(child));

        // Act
        List<FileorFolder> result = projectStructureService.getProjectStructure(projectId);

        // Assert
        assertFalse(result.isEmpty(), "The result should not be empty");
        assertEquals(1, result.size(), "There should be one root node");
        FileorFolder rootNode = result.get(0);
        assertNotNull(rootNode.getChildren(), "Children should not be null");
        assertFalse(rootNode.getChildren().isEmpty(), "Children list should not be empty");
        assertEquals(2, rootNode.getChildren().get(0).getId(), "Child ID should match");

        // Verify interactions
        verify(fileorfolderMapper).findUpFolder(projectId);
        verify(fileorfolderMapper).findByParentId(root.getId());
    }
}
