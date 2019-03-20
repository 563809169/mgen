package ${packageValue};

<#list needImport as i>
    <#if i == "LocalDate">
import java.time.LocalDate;
    </#if>
    <#if i == "LocalDateTime">
import java.time.LocalDateTime;
    </#if>
</#list>
<#if lombok == true>
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
</#if>

/**
 * @author <#if author??>${author}</#if>
 * @date ${date}
 */
<#if lombok == true>
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
</#if>
public class ${className} {

<#list columns as column>
    /**
     *<#if column.isNullable == "YES">可以为null</#if><#if column.defaultValue??>默认值:${column.defaultValue}</#if>
     * ${column.comment}
     */
    private ${column.javaType} ${column.name};
</#list>

<#if lombok == false>
        <#list columns as column>
    public ${column.javaType} get${column.name?cap_first}() {
        return this.${column.name};
    }

    public void set${column.name?cap_first}(${column.javaType} ${column.name}) {
        this.${column.name} = ${column.name};
    }

        </#list>
</#if>


}