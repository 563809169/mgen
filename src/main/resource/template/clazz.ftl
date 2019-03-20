package ${packageValue};

<#list needImport as i>
    <#if i == "LocalDate">
import java.time.LocalDate;
    </#if>
    <#if i == "LocalDateTime">
import java.time.LocalDateTime;
    </#if>
</#list>
<#if lombok == false>
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
</#if>

/**
 * @author ${author}
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
     *
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