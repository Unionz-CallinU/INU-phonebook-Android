package com.example.inuphonebook.Component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inuphonebook.LocalDB.Employee

/** 미사용 resource */
@Composable
fun ProfessorPage(
    professor : Employee
){
    Column(
        modifier = Modifier.fillMaxSize()
    ){

        Icon(
            modifier = Modifier.clip(shape = CircleShape),
            painter = painterResource(professor.photo),
            contentDescription = "Icon",
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 25.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Row(
                    modifier = Modifier.weight(2f)
                ){
                    Text(
                        text = "교수명",
                        fontSize = 20.sp
                    )
                }
                Row(
                    modifier = Modifier.weight(3f)
                ){
                    Text(
                        text = professor.name,
                        fontSize = 20.sp
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Row(
                    modifier = Modifier.weight(2f)
                ){
                    Text(
                        text = "전화번호",
                        fontSize = 20.sp
                    )
                }
                Row(
                    modifier = Modifier.weight(3f)
                ){
                    Text(
                        text = professor.phoneNumber,
                        fontSize = 20.sp
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Row(
                    modifier = Modifier.weight(2f)
                ){
                    Text(
                        text = "주전공",
                        fontSize = 20.sp
                    )
                }
                Row(
                    modifier = Modifier.weight(3f)
                ){
                    Text(
                        text = "주전공",
                        fontSize = 20.sp
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Row(
                    modifier = Modifier.weight(2f)
                ){
                    Text(
                        text = "담당 과목",
                        fontSize = 20.sp
                    )
                }
                Row(
                    modifier = Modifier.weight(3f)
                ){
                    Text(
                        text = "item.major",
                        fontSize = 20.sp
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Row(
                    modifier = Modifier.weight(2f)
                ){
                    Text(
                        text = "연구실",
                        fontSize = 20.sp
                    )
                }
                Row(
                    modifier = Modifier.weight(3f)
                ){
                    Text(
                        text = "item.lab",
                        fontSize = 20.sp
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Row(
                    modifier = Modifier.weight(2f)
                ){
                    Text(
                        text = "이메일",
                        fontSize = 20.sp
                    )
                }
                Row(
                    modifier = Modifier.weight(3f)
                ){
                    Text(
                        text = "item.email",
                        fontSize = 20.sp
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Row(
                    modifier = Modifier.weight(2f)
                ){
                    Text(
                        text = "홈페이지",
                        fontSize = 20.sp
                    )
                }
                Row(
                    modifier = Modifier.weight(3f)
                ){
                    Text(
                        text = "item.homepage",
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}